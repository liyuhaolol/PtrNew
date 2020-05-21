package cn.lyh.spa.ptrnew;

import android.app.Activity;
import android.text.TextUtils;

import com.alibaba.fastjson.TypeReference;

import okhttp3.MediaType;
import spa.lyh.cn.ft_httpcenter.httpbase.BaseRequestCenter;
import spa.lyh.cn.lib_https.listener.DisposeDataListener;
import spa.lyh.cn.lib_https.request.RequestParams;


/**
 * Created by zhaolb on 2017/11/3.
 */

public class RequestCenter extends BaseRequestCenter {
    private static final MediaType MEDIA_TYPE_PNG = MediaType.parse("jpg");

    /**
     * 尝试得到所有的list数据
     */
    public static void getCommonListNoLoading(Activity activity, ListParams params, DisposeDataListener listener){
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
        RequestCenter.getRequest(activity, HttpConstants.ROOT_URL +"/app/all_checked_list.jspx", bodyParams, null, typeReference,null, listener);
    }
}
