package me.goldze.mvvmhabit.base;

import androidx.databinding.ViewDataBinding;

import me.tatarka.bindingcollectionadapter2.BindingRecyclerViewAdapter;

/**
 * @author wengyiheng
 * @date 2020/6/1.
 * description：在布局
 */
public class BaseAdapter extends BindingRecyclerViewAdapter {

    @Override
    public void onBindBinding(ViewDataBinding binding, int variableId, int layoutRes, int position, Object item) {
        super.onBindBinding(binding, variableId, layoutRes, position, item);
    }
}
