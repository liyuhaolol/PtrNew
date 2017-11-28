package cn.lyh.spa.ptrnew;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

/**
 * Created by liyuhao on 2017/5/24.
 * 刷新框架，尾布局
 */

public class FooterView extends FrameLayout {
    private TextView ptr_footer_title;

    private View view;

    private boolean result;
    private boolean isLastPage;


    public FooterView(Context context) {
        this(context, null);
    }

    public FooterView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FooterView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView(){
        view = LayoutInflater.from(getContext()).inflate(R.layout.ptr_view_footer, this, false);
        addView(view);
        ptr_footer_title = (TextView) view.findViewById(R.id.ptr_footer_title);
    }


}
