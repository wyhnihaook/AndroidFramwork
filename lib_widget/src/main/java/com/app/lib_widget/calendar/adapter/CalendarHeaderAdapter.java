package com.app.lib_widget.calendar.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.lib_widget.R;

/**
 * @author wengyiheng
 * @date 2021/9/6.
 * description：顶部日期信息展示
 */
public class CalendarHeaderAdapter  extends RecyclerView.Adapter {


    private String[]  list;

    public CalendarHeaderAdapter(String[]  list) {
        this.list = list;
    }

    class ItemChildViewHolder extends RecyclerView.ViewHolder {

        TextView day;


        public ItemChildViewHolder(View view) {
            super(view);

            day = view.findViewById(R.id.calendar_week_item_day);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.calendar_week_item, parent, false);
        final ItemChildViewHolder holder = new ItemChildViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        String item = list[position];
        //数据赋值
        ItemChildViewHolder viewHolder = (ItemChildViewHolder) holder;

        if(position==0||position==list.length-1){
            viewHolder.day.setTextColor(Color.parseColor("#FA843F"));
        }else{
            viewHolder.day.setTextColor(Color.parseColor("#FFFFFF"));
        }

        viewHolder.day.setText(item);

    }

    @Override
    public int getItemCount() {
        return list.length;
    }


}
