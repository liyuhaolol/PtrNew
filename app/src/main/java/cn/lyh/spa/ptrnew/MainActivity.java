package cn.lyh.spa.ptrnew;

import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

import cns.workspace.lib.androidsdk.httputils.listener.DisposeDataListener;


public class MainActivity extends AppCompatActivity {
    Handler handler;
    RecyclerView item_recy;

    ListItemAdapter adapter;
    BaseQuickAdapter mm;

    FooterView footerView;

    TextView ptr_footer_title;

    private List<ItemData> list;

    int currentPage = 1;
    int maxPage = 0;
    ListParams lp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        handler = new Handler(Looper.getMainLooper());
        item_recy = findViewById(R.id.item_recy);
        list = new ArrayList<>();
        footerView = new FooterView(this);
        ptr_footer_title = footerView.findViewById(R.id.ptr_footer_title);
        adapter = new ListItemAdapter(this,list);
        item_recy.setLayoutManager(new LinearLayoutManager(this));
        item_recy.setAdapter(adapter);
        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                loadMore();
            }
        },item_recy);
        adapter.setLoadMoreView(new MyLoadMoreView());
        adapter.setEnableLoadMore(false);

    }

    @Override
    protected void onResume() {
        super.onResume();
        lp = new ListParams();
        lp.user_id = 2;
        lp.pageNo = 1;
        lp.pageSize = 10;

        RequestCenter.getCommonListNoLoading(this, lp, new DisposeDataListener() {
            @Override
            public void onSuccess(Object responseObj) {
                ListData ld = (ListData) responseObj;
                list.addAll(ld.list);
                adapter.notifyDataSetChanged();
                adapter.setEnableLoadMore(true);
                //adapter.setFooterView(footerView);
                currentPage = lp.pageNo;
                maxPage = ld.totalPage;
            }

            @Override
            public void onFailure(Object reasonObj) {
                Toast.makeText(MainActivity.this,"报错",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadMore() {
        if (lp.pageNo == maxPage){
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    adapter.loadMoreEnd();
                }
            },500);
        }else {
            lp.pageNo = currentPage+1;
            RequestCenter.getCommonListNoLoading(this, lp, new DisposeDataListener() {
                @Override
                public void onSuccess(Object responseObj) {
                    ListData ld = (ListData) responseObj;
                    list.addAll(ld.list);
                    adapter.notifyDataSetChanged();
                    adapter.setEnableLoadMore(true);
                    //adapter.setFooterView(footerView);
                    currentPage = lp.pageNo;
                    maxPage = ld.totalPage;
                    adapter.loadMoreComplete();
                }

                @Override
                public void onFailure(Object reasonObj) {
                    adapter.loadMoreFail();
                }
            });
        }
    }
}
