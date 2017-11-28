package cn.lyh.spa.ptrnew;

import com.chad.library.adapter.base.loadmore.LoadMoreView;

/**
 * Created by liyuhao on 2017/11/27.
 */

public class MyLoadMoreView extends LoadMoreView{
    @Override
    public int getLayoutId() {
        return R.layout.test_view_load_more;
    }

    @Override
    protected int getLoadingViewId() {
        return R.id.load_more_loading_view;
    }

    @Override
    protected int getLoadFailViewId() {
        return R.id.load_more_load_fail_view;
    }

    @Override
    protected int getLoadEndViewId() {
        return R.id.load_more_load_end_view;
    }
}
