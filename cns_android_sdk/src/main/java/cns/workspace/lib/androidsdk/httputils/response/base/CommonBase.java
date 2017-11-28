package cns.workspace.lib.androidsdk.httputils.response.base;

/**
 * Created by liyuhao on 2017/8/29.
 */

public class CommonBase {
    public final String TAG = "cns_sdk_net";
    /**
     * the logic layer exception, may alter in different app
     *
     */
    public final String NET_MSG = "网络或服务器连接失败";
    public final String EMPTY_MSG = "未知错误";
    public final String JSON_MSG = "JSON数据有误";
    public final String JSON_MSG_TYPEREFERENCE = "得到非商议协议格式JSON数据";
    public final String CANCEL_MSG = "请求取消";
    public final String IO_LENGTH_MSG = "无法得到文件长度";
    public final String IO_NET_MSG = "网络链接中断,无法继续下载";
    public final String COOKIE_STORE = "Set-Cookie"; // decide the server it
    // can has the value of
    // set-cookie2
}
