package com.app.lib_widget.swiprecyclerview.adapter;

import android.content.Context;
import android.view.LayoutInflater;

import androidx.recyclerview.widget.RecyclerView;

/**
 * @author wengyiheng
 * @date 2021/6/28.
 * description：基类信息
 */
public abstract class BaseSwipeAdapter<VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {

    private LayoutInflater mInflater;

    public BaseSwipeAdapter(Context context) {
        this.mInflater = LayoutInflater.from(context);
    }

    public LayoutInflater getInflater() {
        return mInflater;
    }

}