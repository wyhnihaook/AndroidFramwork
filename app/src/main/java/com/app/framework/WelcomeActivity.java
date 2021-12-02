package com.app.framework;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;

import androidx.annotation.RequiresApi;


import com.alibaba.android.arouter.launcher.ARouter;
import com.app.framework.databinding.AppActivityWelcomeBinding;

import me.goldze.mvvmhabit.base.BaseActivity;
import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.router.path.RouterActivityPath;
import me.goldze.mvvmhabit.utils.SPUtils;
import me.goldze.mvvmhabit.utils.StatusBarUtil;
import me.goldze.mvvmhabit.utils.recovery.AppStatus;
import me.goldze.mvvmhabit.utils.recovery.AppStatusManager;

/**
 * @author wengyiheng
 * @date 2021/9/15.
 * description：欢迎页面
 */
public class WelcomeActivity extends BaseActivity<AppActivityWelcomeBinding, BaseViewModel> {


    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.app_activity_welcome;
    }

    @Override
    public int initVariableId() {
        return BR.baseViewModel;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void initData() {
        super.initData();

        StatusBarUtil.setSystemBar(this, getResources().getColor(R.color.transport), false, true);

        //保存状态
        if ((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0) {
            finish();
            return;
        }

        init();
    }


    public void init() {

        binding.screen.postDelayed(new Runnable() {
            @Override
            public void run() {
                doNext();
            }
        }, 1000);
    }


    private void doNext() {
        //判断本地有token直接登录首页
        String token = SPUtils.getInstance().getString("token");

        AppStatusManager.getInstance().setAppStatus(AppStatus.STATUS_NORMAL);

        if (!TextUtils.isEmpty(token)) {

            //首页
            ARouter.getInstance().build(RouterActivityPath.Main.PAGER_MAIN).navigation();

        } else {

            //登录页面
            ARouter.getInstance().build(RouterActivityPath.Sign.PAGER_LOGIN).navigation();
        }

        viewModel.finish();
    }
}
