package cns.workspace.lib.androidsdk.httputils.request;

import android.util.Log;

import java.util.Map;

import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.Request;

/**
 * @author 李宇昊
 * @function 接受请求参数，为我们生成request对象
 */
public class CommonRequest {
    /**
     * create the key-value Request
     *
     * @param url 链接
     * @param params 参数
     * @return request
     */
    public static Request createPostRequest(String url, RequestParams params) {
        return createPostRequest(url, params, null);
    }

    /**可以带请求头的Post请求
     * @param url 链接
     * @param params 参数
     * @param headers 头文件
     * @return request
     */
    public static Request createPostRequest(String url, RequestParams params, RequestParams headers) {
        FormBody.Builder mFormBodyBuild = new FormBody.Builder();
        if (params != null) {
            for (Map.Entry<String, String> entry : params.urlParams.entrySet()) {
                //将请求参数遍历添加到我们的请求构建类中
                mFormBodyBuild.add(entry.getKey(), entry.getValue());
            }
        }
        //添加请求头
        Headers.Builder mHeaderBuild = new Headers.Builder();
        if (headers != null) {
            for (Map.Entry<String, String> entry : headers.urlParams.entrySet()) {
                mHeaderBuild.add(entry.getKey(), entry.getValue());
            }
        }
        //通过请求构建类的build方法获取真正的请求对象
        FormBody mFormBody = mFormBodyBuild.build();
        //生成header
        Headers mHeader = mHeaderBuild.build();
        Request request = new Request.Builder().url(url).
                post(mFormBody).
                headers(mHeader)
                .build();
        return request;
    }

    /**
     * ressemble the params to the url
     *
     * @param url 链接
     * @param params 参数
     * @return request
     */
    public static Request createGetRequest(String url, RequestParams params) {

        return createGetRequest(url, params, null,false);
    }

    /**
     * 可以带请求头的Get请求
     * @param url 链接
     * @param params 参数
     * @param headers 头文件
     * @return request
     */
    public static Request createGetRequest(String url, RequestParams params, RequestParams headers,boolean isDev) {
        StringBuilder urlBuilder = new StringBuilder(url).append("?");
        if (params != null) {
            for (Map.Entry<String, String> entry : params.urlParams.entrySet()) {
                urlBuilder.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
            }
        }
        //添加请求头
        Headers.Builder mHeaderBuild = new Headers.Builder();
        if (headers != null) {
            for (Map.Entry<String, String> entry : headers.urlParams.entrySet()) {
                mHeaderBuild.add(entry.getKey(), entry.getValue());
            }
        }
        Headers mHeader = mHeaderBuild.build();
        String webUrl = urlBuilder.substring(0, urlBuilder.length() - 1);//去掉末尾的&
        if (isDev){
            Log.e("WebUrl",webUrl);
        }
        return new Request.Builder().
                url(webUrl)
                .get()
                .headers(mHeader)
                .build();
    }
}