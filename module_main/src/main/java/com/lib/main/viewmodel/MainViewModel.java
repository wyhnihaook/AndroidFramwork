package com.lib.main.viewmodel;

import android.app.Application;


import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;

import me.goldze.mvvmhabit.base.BaseActivity;
import me.goldze.mvvmhabit.base.BaseRxViewModel;

/**
 * @author wengyiheng
 * @date 2021/8/24.
 * description：首页信息
 */
public class MainViewModel extends BaseRxViewModel {

    public ObservableField<BaseActivity> activityObservableField=new ObservableField<>();


    public MainViewModel(@NonNull Application application) {
        super(application);
    }

}
