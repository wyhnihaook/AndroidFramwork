package com.lib.schedule;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.lib.schedule.databinding.ScheduleFragmentHomeBinding;
import com.lib.schedule.viewmodel.ScheduleHomeViewModel;
import com.lib.schedule.BR;

import io.reactivex.annotations.Nullable;
import me.goldze.mvvmhabit.base.BaseFragment;
import me.goldze.mvvmhabit.router.path.RouterFragmentPath;

/**
 * @author wengyiheng
 * @date 2021/9/17.
 * description：
 */
@Route(path = RouterFragmentPath.Schedule.PAGER_SCHEDULE)
public class ScheduleHomeFragment extends BaseFragment<ScheduleFragmentHomeBinding, ScheduleHomeViewModel> {

    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.schedule_fragment_home;
    }

    @Override
    public int initVariableId() {
        return BR.scheduleHomeViewModel;
    }

    @Override
    public void initData() {
        super.initData();
    }
}
