package cn.lyh.spa.ptrnew;

import android.app.Activity;
import android.text.TextUtils;

import com.alibaba.fastjson.TypeReference;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cns.workspace.lib.androidsdk.httputils.CommonOkHttpClient;
import cns.workspace.lib.androidsdk.httputils.listener.DisposeDataHandle;
import cns.workspace.lib.androidsdk.httputils.listener.DisposeDataListener;
import cns.workspace.lib.androidsdk.httputils.request.CommonRequest;
import cns.workspace.lib.androidsdk.httputils.request.RequestParams;
import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by zhaolb on 2017/11/3.
 */

public class RequestCenter {
    private static final MediaType MEDIA_TYPE_PNG = MediaType.parse("jpg");

    //根据参数发送所有post请求
    public static Call postRequest(final Activity activity, String url, RequestParams params, RequestParams headers, final DisposeDataListener listener, TypeReference<?> typeReference) {
        return postRequest(activity,url,params,headers,listener,typeReference,true);
    }

    //可以控制是否显示loadingDialog
    public static Call postRequest(final Activity activity, String url, RequestParams params, RequestParams headers, final DisposeDataListener listener, TypeReference<?> typeReference, boolean showDialog) {
        //初始化等待loadDialog并显示
        //创建网络请求
        Call call = CommonOkHttpClient.sendResquest(CommonRequest.
                createPostRequest(url, params,headers), new DisposeDataHandle(new DisposeDataListener() {
            @Override
            public void onSuccess(Object responseObj) {
                listener.onSuccess(responseObj);
            }

            @Override
            public void onFailure(Object reasonObj) {
                listener.onFailure(reasonObj);

            }
        }, typeReference, true));

        return call;
    }

    //可以控制是否显示loadingDialog
    public static Call postFileRequest(final Activity activity, Request request, final DisposeDataListener listener, TypeReference<?> typeReference, boolean showDialog) {
        //初始化等待loadDialog并显示
        //创建网络请求
        Call call = CommonOkHttpClient.sendResquest(request, new DisposeDataHandle(new DisposeDataListener() {
            @Override
            public void onSuccess(Object responseObj) {
                listener.onSuccess(responseObj);
            }

            @Override
            public void onFailure(Object reasonObj) {
                listener.onFailure(reasonObj);

            }
        }, typeReference, true));

        return call;
    }


    //根据参数发送所有get请求
    public static void getRequest(Activity activity, String url, RequestParams params, RequestParams headers, DisposeDataListener listener, TypeReference<?> typeReference) {
        getRequest(activity, url, params, headers, listener, typeReference, true);
    }

    //可以控制是否显示loadingDialog
    public static Call getRequest(final Activity activity, String url, RequestParams params, RequestParams headers, final DisposeDataListener listener, TypeReference<?> typeReference, boolean showDialog) {
        //初始化等待loadDialog并显示
        //创建网络请求
        Call call = CommonOkHttpClient.sendResquest(CommonRequest.
                createGetRequest(url, params,headers,true), new DisposeDataHandle(new DisposeDataListener() {
            @Override
            public void onSuccess(Object responseObj) {
                listener.onSuccess(responseObj);
            }

            @Override
            public void onFailure(Object reasonObj) {
                listener.onFailure(reasonObj);

            }
        }, typeReference, true));

        return call;
    }


    /**
     * 尝试得到所有的list数据
     */
    public static void getCommonListNoLoading(Activity activity, ListParams params,DisposeDataListener listener){
        RequestParams bodyParams = new RequestParams();
        bodyParams.put("user_id",String.valueOf(params.user_id));
        bodyParams.put("pageNo",String.valueOf(params.pageNo));
        bodyParams.put("pageSize",String.valueOf(params.pageSize));
        if (!params.title.equals("")){
            bodyParams.put("title",params.title);
        }
        if (!params.tag.equals("")){
            bodyParams.put("tag",params.tag);
        }
        if (!params.author.equals("")){
            bodyParams.put("author",params.author);
        }
        if (!(params.lxId == -1)){
            bodyParams.put("lxId",params.lxId+"");
        }
        if (!(params.districtId == -1)){
            bodyParams.put("districtId",params.districtId+"");
        }
        if (!params.beginTime.equals("")){
            bodyParams.put("beginTime",params.beginTime);
        }
        if (!params.endTime.equals("")){
            bodyParams.put("endTime",params.endTime);
        }
        if (!(params.isReceived == -1)){
            bodyParams.put("isReceived",params.isReceived+"");
        }
        //Log.e("CommonListUrl",HttpConstants.ROOT_URL +Global.MAIN_URL);
        TypeReference typeReference = new TypeReference<ListData>(){};
        RequestCenter.getRequest(activity, HttpConstants.ROOT_URL +"/app/all_checked_list.jspx", bodyParams, null, listener, typeReference,false);
    }
}
