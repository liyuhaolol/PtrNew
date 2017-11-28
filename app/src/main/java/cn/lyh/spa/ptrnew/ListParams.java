package cn.lyh.spa.ptrnew;

/**
 * Created by liyuhao on 2017/11/10.
 */

public class ListParams extends BaseEntity{
    public int user_id;
    public int pageNo;
    public int pageSize;
    public String title = "";
    public String tag = "";
    public String author = "";
    public int lxId = -1;
    public int districtId = -1;
    public String beginTime = "";
    public String endTime = "";
    public int isReceived = -1;
}
