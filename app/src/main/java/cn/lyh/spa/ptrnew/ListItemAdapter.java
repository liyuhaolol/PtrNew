package cn.lyh.spa.ptrnew;

import android.content.Context;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;


/**
 * Created by liyuhao on 2017/11/13.
 */

public class ListItemAdapter extends BaseQuickAdapter<ItemData,ListItemAdapter.myViewHolder> {
    private Context context;

    private List<ItemData> list;

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
//    public ListItemAdapter(List<ItemData> data) {
//        super(data);
//    }

    public ListItemAdapter(Context context,List<ItemData> data){
        super(R.layout.item_main_list,data);
        this.context = context;
        this.list = data;
    }


    /*@Override
    public void onBindViewHolder(myViewHolder holder, final int position) {
        ItemData data = list.get(position);
        String mTitle;
        if (data.receive){
            mTitle = "[已签收]"+data.title;
            SpannableStringBuilder builder = new SpannableStringBuilder(mTitle);
            ForegroundColorSpan redSpan = new ForegroundColorSpan(Color.RED);
            builder.setSpan(redSpan, 0, 5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            holder.tv_draft_title.setText(builder);
        }else {
            holder.tv_draft_title.setText(data.title);
        }
        if (data.author.equals("")){
            holder.view_vertical_line.setVisibility(View.GONE);
        }else {
            holder.view_vertical_line.setVisibility(View.VISIBLE);
            holder.tv_draft_author.setText(data.author);
        }
        holder.tv_release_time.setText(data.date);
        if (data.lv == 1){
            holder.tv_urgent.setVisibility(View.GONE);
        }else if (data.lv == 2){
            holder.tv_urgent.setVisibility(View.VISIBLE);
        }
        if (!data.lx.name.equals("")){
            holder.tv_type.setText("["+data.lx.name+"]");
        }
        if (!data.district.equals("")){
            holder.tv_area.setText("["+data.district+"]");
        }
        holder.tv_state.setText(data.status);
    }*/

    @Override
    protected void convert(myViewHolder holder, ItemData item) {
        ItemData data = list.get(holder.getAdapterPosition());
        String mTitle;
        if (data.receive){
            mTitle = "[已签收]"+data.title;
            SpannableStringBuilder builder = new SpannableStringBuilder(mTitle);
            ForegroundColorSpan redSpan = new ForegroundColorSpan(Color.RED);
            builder.setSpan(redSpan, 0, 5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            holder.tv_draft_title.setText(builder);
        }else {
            holder.tv_draft_title.setText(data.title);
        }
        if (data.author.equals("")){
            holder.view_vertical_line.setVisibility(View.GONE);
        }else {
            holder.view_vertical_line.setVisibility(View.VISIBLE);
            holder.tv_draft_author.setText(data.author);
        }
        holder.tv_release_time.setText(data.date);
        if (data.lv == 1){
            holder.tv_urgent.setVisibility(View.GONE);
        }else if (data.lv == 2){
            holder.tv_urgent.setVisibility(View.VISIBLE);
        }
        if (!data.lx.name.equals("")){
            holder.tv_type.setText("["+data.lx.name+"]");
        }
        if (!data.district.equals("")){
            holder.tv_area.setText("["+data.district+"]");
        }
        holder.tv_state.setText(data.status);
    }


    class myViewHolder extends BaseViewHolder{
        TextView tv_draft_title,tv_draft_author,tv_release_time,tv_urgent,tv_type,tv_area,tv_state;
        View view_vertical_line;
        LinearLayout item_click;

        private myViewHolder(View v){
            super(v);
            view_vertical_line = v.findViewById(R.id.view_vertical_line);
            tv_draft_title = v.findViewById(R.id.tv_draft_title);
            tv_draft_author = v.findViewById(R.id.tv_draft_author);
            tv_release_time = v.findViewById(R.id.tv_release_time);
            tv_urgent = v.findViewById(R.id.tv_urgent);
            tv_type = v.findViewById(R.id.tv_type);
            tv_area = v.findViewById(R.id.tv_area);
            tv_state = v.findViewById(R.id.tv_state);
            item_click = v.findViewById(R.id.item_click);

        }
    }
}
