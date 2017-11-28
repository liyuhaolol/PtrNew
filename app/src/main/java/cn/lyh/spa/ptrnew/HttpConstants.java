package cn.lyh.spa.ptrnew;

/**
 * Created by zhaolb on 2017/11/3.
 */

public class HttpConstants {
    //项目入口
    //测试地址
    public static final String PIC_URL = "http://1.202.234.148:8080";

    //唐
    //public static final String PIC_URL = "http://10.9.1.196:8080";

    //付
    //public static final String PIC_URL = "http://10.9.1.158:8080";

    public static final String ROOT_URL = PIC_URL+"/mxcb";


    //登录接口
    public static final String LOGIN = ROOT_URL + "/app/login.jspx";

    //菜单列表接口
    public static final String MENU_URL = ROOT_URL + "/app/getMenu.jspx";

    //获取发稿信息接口
    public static final String GETSENDINFO_URL = ROOT_URL + "/app/fgSelect.jspx";

    //获取类型接口
    public static final String GET_TYPE = ROOT_URL + "/app/selectLx.jspx";

    //获取地域接口
    public static final String GET_AREA = ROOT_URL + "/app/selectDistrict.jspx";

    //签收文章
    public static final String SIGN_ARTICLE = ROOT_URL + "/app/receive.jspx";

    //审核详情接口
    public static final String GET_CHECK_DETAIL = ROOT_URL + "/app/getDetail_check.jspx";

    //已审稿库详情接口
    public static final String GET_YISHEN_DETAIL = ROOT_URL + "/app/getDetail_modify.jspx";

    //解除稿件锁定接口
    public static final String RELEASE_LOCK = ROOT_URL + "/app/releaseLock.jspx";

    //发稿接口
    public static final String ARTICLE_ADD = ROOT_URL + "/app/article_add.jspx";

    //草稿箱接口
    public static final String GET_DETAIL_EDIT = ROOT_URL + "/app/getDetail_edit.jspx";

    //删除稿件接口
    public static final String ARTICLE_DELETE = ROOT_URL + "/app/article_delete.jspx";

    //草稿箱编辑接口
    public static final String ARTICLE_EDIT = ROOT_URL + "/app/article_edit.jspx";

    //审核接口
    public static final String ARTICLE_CHECK = ROOT_URL + "/app/article_check.jspx";

    //修改详情接口
    public static final String GET_DETAIL_MODIFY = ROOT_URL + "/app/getDetail_modify.jspx";

    //修改接口
    public static final String ARTICLE_MODIFY_AFTER_CHECK = ROOT_URL + "/app/article_modifyAftercheck.jspx";

    //退稿接口
    public static final String ARTICLE_SEND_BACK = ROOT_URL + "/app/article_sendback.jspx";

    //功能介绍接口
    public static final String GET_INTRODUCTION = ROOT_URL + "/app/getIntroduction.jspx";

    //修改密码接口
    public static final String MODIFY_PASSWORD = ROOT_URL + "/app/modifyPwd.jspx";

    //检查更新接口
    public static final String GET_VERSION = ROOT_URL + "/app/getVersion.jspx";

    /**
     * 正式服务器下载apk接口
     */
    public static final String UPDATE_APK_URL = "http://app.newszu.com:8080/newszu.apk";
}
