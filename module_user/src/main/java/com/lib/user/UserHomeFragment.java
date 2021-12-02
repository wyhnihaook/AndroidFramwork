package com.lib.user;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.lib.user.BR;
import com.lib.user.R;
import com.lib.user.databinding.UserFragmentHomeBinding;
import com.lib.user.viewmodel.UserHomeViewModel;


import io.reactivex.annotations.Nullable;
import me.goldze.mvvmhabit.base.BaseFragment;
import me.goldze.mvvmhabit.router.path.RouterFragmentPath;

/**
 * @author wengyiheng
 * @date 2021/9/17.
 * description：
 */
@Route(path = RouterFragmentPath.User.PAGER_USER)
public class UserHomeFragment extends BaseFragment<UserFragmentHomeBinding, UserHomeViewModel> {

    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.user_fragment_home;
    }

    @Override
    public int initVariableId() {
        return BR.userHomeViewModel;
    }

    @Override
    public void initData() {
        super.initData();
    }
}
