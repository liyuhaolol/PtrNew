package cn.lyh.spa.ptrnew;

import android.os.Handler;
import android.os.Looper;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

import spa.lyh.cn.lib_https.listener.DisposeDataListener;


public class MainActivity extends AppCompatActivity {
    Handler handler;
    RecyclerView item_recy;

    ListItemAdapter adapter;

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
        adapter.getLoadMoreModule().setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                loadMore();
            }
        });
        adapter.getLoadMoreModule().setLoadMoreView(new MyLoadMoreView());
        adapter.getLoadMoreModule().setEnableLoadMore(false);

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
                adapter.getLoadMoreModule().setEnableLoadMore(true);
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
                    adapter.getLoadMoreModule().loadMoreEnd();
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
                    adapter.getLoadMoreModule().setEnableLoadMore(true);
                    //adapter.setFooterView(footerView);
                    currentPage = lp.pageNo;
                    maxPage = ld.totalPage;
                    adapter.getLoadMoreModule().loadMoreComplete();
                }

                @Override
                public void onFailure(Object reasonObj) {
                    adapter.getLoadMoreModule().loadMoreFail();
                }
            });
        }
    }
}
