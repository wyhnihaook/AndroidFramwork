package com.lib.user.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;

import me.goldze.mvvmhabit.base.BaseFragment;
import me.goldze.mvvmhabit.base.BaseRxViewModel;

/**
 * @author wengyiheng
 * @date 2021/8/24.
 * description：首页信息
 */
public class UserHomeViewModel extends BaseRxViewModel {

    public ObservableField<BaseFragment> activityObservableField=new ObservableField<>();


    public UserHomeViewModel(@NonNull Application application) {
        super(application);
    }

}
