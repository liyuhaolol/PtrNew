package cn.lyh.spa.ptrnew;


/**
 * Created by liyuhao on 2017/11/13.
 */

public class ItemData extends BaseEntity {


    public int id; //稿件编号
    public String version = "";//版本
    public int lv; //级别，1普通2加急
    public String title; //主题
    public String author =""; //作者
    public DraftCategory lx; //类型
    public String district = "";//地域
    public String date = "";
    public boolean ifLock;
    public String status ="";
    public boolean receive = false;
    public int versionId;

}
