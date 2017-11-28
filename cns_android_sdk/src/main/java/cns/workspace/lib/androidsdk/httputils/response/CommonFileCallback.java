package cns.workspace.lib.androidsdk.httputils.response;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;

import cns.workspace.lib.androidsdk.httputils.exception.OkHttpException;
import cns.workspace.lib.androidsdk.httputils.listener.DisposeDataHandle;
import cns.workspace.lib.androidsdk.httputils.listener.DisposeDownloadListener;
import cns.workspace.lib.androidsdk.httputils.model.Progress;
import cns.workspace.lib.androidsdk.httputils.response.base.CommonBase;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


/**
 * *******************************************************
 *
 * @文件名称：CommonFileCallback.java
 * @文件作者：liyuhao
 * @创建时间：2017年9月12日 下午5:32:01
 * @文件描述：专门处理文件下载回调
 * @修改历史：2016年1月23日创建初始版本
 *
 * ********************************************************
 */
public class CommonFileCallback extends CommonBase implements Callback {
    /**
     * 默认的错误文件代替名
     */
    private static final String DEAFULT_FILE_NAME = "deafult";

    /**
     * 将其它线程的数据转发到UI线程
     */
    private static final int PROGRESS_MESSAGE = 0x01;
    private Handler mDeliveryHandler;
    private DisposeDownloadListener mListener;
    private String mFilePath;
    private String filename;
    private String filePath;
    private boolean devMode;

    public CommonFileCallback(DisposeDataHandle handle) {
        this.mListener = handle.downloadListener;
        this.mFilePath = handle.mSource;
        this.devMode = handle.devMode;
        this.mDeliveryHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case PROGRESS_MESSAGE:
                        Progress p = (Progress) msg.obj;
                        mListener.onProgress(p.progress,p.currentSize,p.sumSize);
                        break;
                }
            }
        };
    }

    @Override
    public void onFailure(final Call call, final IOException ioexception) {
        mDeliveryHandler.post(new Runnable() {
            @Override
            public void run() {
                mListener.onFailure(new OkHttpException(OkHttpException.NETWORK_ERROR, NET_MSG,""));
            }
        });
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        if (response.code() == 200){
            handleResponse(response);
        }else {
            mListener.onFailure(new OkHttpException(OkHttpException.SERVER_ERROR, response.code()+"",response.code()));
        }

    }


    /**
     * 此时还在子线程中，不则调用回调接口
     *
     * @param response
     * @return
     */
    private void  handleResponse(Response response){
        if (response == null) {
            //习惯性判空，理论不会空
            mListener.onFailure(new OkHttpException(OkHttpException.OTHER_ERROR, EMPTY_MSG,"response is null"));
            return;
        }

        filename = getFileName(response);

        if (devMode){
            Log.e(TAG,"filename:"+filename);
        }

////////////////////////////


        int length;//每一块的长度
        byte[] buffer = new byte[2048];//每一段的长度
        InputStream inputStream = null;
        checkLocalFilePath(mFilePath);
        filePath = mFilePath+"/"+filename;
        File file = new File(filePath);
        FileOutputStream fos = null;


        int currentLength = 0;//当前已经下载的大小
        int lastSize = -1;
        long sumLength;
        float mProgress;

        Progress p;
        try {

            inputStream  = response.body().byteStream();//输入流
            fos  = new FileOutputStream(file);
            sumLength = response.body().contentLength();//文件总大小


            if (sumLength > 0){
                //这里应该发送一下总大小，已经进度为0
                p = new Progress();
                p.progress = 0;
                p.currentSize = convertFileSize(0);
                p.sumSize = convertFileSize(sumLength);
                mDeliveryHandler.obtainMessage(PROGRESS_MESSAGE, p).sendToTarget();
                while ((length = inputStream.read(buffer)) != -1) {
                    fos.write(buffer, 0, length);
                    currentLength += length;
                    //计算百分比
                    mProgress = getNumber((float)currentLength/(float)sumLength)*100;
                    int currentSize = (int) mProgress;
                    //判断整型进度去重只传100次0到100
                    if (currentSize != lastSize){
                        lastSize = currentSize;
                        p = new Progress();
                        p.progress = lastSize;
                        p.currentSize = convertFileSize(currentLength);
                        p.sumSize = convertFileSize(sumLength);
                        if (devMode){
                            Log.e(TAG,p.progress+"%   "+p.currentSize+"/"+p.sumSize);
                        }
                        mDeliveryHandler.obtainMessage(PROGRESS_MESSAGE, p).sendToTarget();
                    }
                }
                fos.flush();
            }else {
                //输出失败，解析错误
                if (devMode){
                    Log.e(TAG,IO_LENGTH_MSG);
                }
                mListener.onFailure(new OkHttpException(OkHttpException.IO_ERROR, IO_LENGTH_MSG,sumLength));
                return;
            }

        } catch (Exception e) {
            e.printStackTrace();
            mListener.onFailure(new OkHttpException(OkHttpException.IO_ERROR, IO_NET_MSG,e.getMessage()));
            return;
        } finally {
            try {
                if (fos != null){
                    fos.close();
                }
                if (inputStream != null) {

                    inputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        mListener.onSuccess(filePath,filename);
    }

    private void checkLocalFilePath(String localFilePath) {
        File path = new File(localFilePath);
        if (!path.exists()) {
            path.mkdirs();
        }
    }

    /**
     * 计算文件大小<P/>
     * Created by liyuhao on 2016/3/24.<P/>
     * @param size 字节数
     * @return 对应的G，M，K
     */
    public String convertFileSize(long size) {
        long kb = 1024;
        long mb = kb * 1024;
        long gb = mb * 1024;

        if (size >= gb) {
            return String.format("%.2f GB", (float) size / gb);
        } else if (size >= mb) {
            float f = (float) size / mb;
            return String.format(f > 100 ? "%.0f MB" : "%.2f MB", f);
        } else if (size >= kb) {
            float f = (float) size / kb;
            return String.format(f > 100 ? "%.0f KB" : "%.2f KB", f);
        } else
            return String.format("%d B", size);
    }

    /**
     * 得到对应位数小数<P/>
     * Created by liyuhao on 2016/3/24.<P/>
     * @param number float的数
     * @return float的数
     */
    public float getNumber(float number){
        DecimalFormat df = new DecimalFormat("#.##############");
        float f=Float.valueOf(df.format(number));
        return f;
    }

    /**
     * 得到文件名
     * @param response response获取文件名
     * @return 文件名
     */
    private String getFileName(Response response){
        boolean flag = false;//判断是否截取url文件名
        String filename = DEAFULT_FILE_NAME;
        String content = response.header("Content-Disposition");
        if (content != null){
            int startindex = content.indexOf("filename=");
            if (startindex != -1){
                String frontPart = content.substring(startindex+9);
                int endindex = frontPart.indexOf(";");
                if (endindex != -1){
                    filename = frontPart.substring(0,endindex);
                }else {
                    filename = frontPart;
                }
            }else {
                flag = true;
            }

        }else {
            flag = true;
        }

        if (flag){
            String url = response.request().url().toString();
            filename = url.substring(url.lastIndexOf("/")+1);
        }
        return getRealFileName(filename);
    }

    /**
     * 得到正确的文件名
     * @param fileName 文件名
     * @return 返回正确的文件名
     */
    private String getRealFileName(String fileName){
        if (fileName.equals("")){
            return DEAFULT_FILE_NAME;
        }


        String front;
        String behind = "";
        int  spot = fileName.lastIndexOf(".");
        if (spot == 0){
            //点在首位
            front = DEAFULT_FILE_NAME;
            behind = fileName.substring(spot);

        }else if (spot == (fileName.length()-1)){
            //点在末尾
            front = fileName.substring(0,spot);
        }else if (spot != -1){
            //点在中间
            front = fileName.substring(0,spot);
            behind = fileName.substring(spot);
        }else {
            //不存在点
            front = fileName;
        }
        //如果只有.则去除后缀
        if(behind.equals(".")){
            behind = "";
        }
        //已经将文件名按照.分为2段,分别验证是否合法
        front = syncFileName(front,false);
        behind = syncFileName(behind,true);

        return front+behind;
    }

    /**
     * 分析输入字符串是否符合文件命名规则
     * @param contnet 被分析内容
     * @param isSuffix 是否为后缀
     * @return 返回分析结果
     */
    private String syncFileName(String contnet,boolean isSuffix){
        //取得内容
        String syncContent = contnet;
        //判空,因为name有初值，所以这里判断是否存在后缀，没有就没有
        if (syncContent.equals("")){
            return syncContent;
        }

        //
        int flag = contnet.length();

        String[] patterns = new String[]{"\\","/",":","*","?","\"","<",">","|"};

        for (String pattern:patterns){
            int index = contnet.indexOf(pattern);
            if (index != -1){
                if (index < flag){
                    flag = index;
                }
            }
        }
        //取得最近非法字符index
        if (isSuffix){
            //当为后缀时,flag必然不为0
            if (flag == 1){
                //点后第一位就是非法字符
                return "";
            }
        }else {
            //当为name时
            if (flag == 0){
                //name第一位就是非法字符
                return DEAFULT_FILE_NAME;
            }
        }

        if (flag < contnet.length()){
            syncContent = syncContent.substring(0,flag);
        }

        return syncContent;

    }
}