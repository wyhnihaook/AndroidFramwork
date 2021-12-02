package com.lib.login;

import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;


import com.alibaba.android.arouter.facade.annotation.Route;
import com.lib.login.databinding.LoginActivityLoginViewBinding;
import com.lib.login.viewmodel.LoginViewModel;

import me.goldze.mvvmhabit.base.BaseAppActivity;

import me.goldze.mvvmhabit.base.OnRxBusListener;
import me.goldze.mvvmhabit.router.path.RouterActivityPath;
import me.goldze.mvvmhabit.utils.Constants;
import me.goldze.mvvmhabit.utils.EventCenter;
import me.goldze.mvvmhabit.utils.KeyBoardUtils;
import me.goldze.mvvmhabit.utils.StatusBarUtil;
import me.goldze.mvvmhabit.utils.recovery.AppStatus;
import me.goldze.mvvmhabit.utils.recovery.AppStatusManager;

/**
 * @author wengyiheng
 * @date 2021/8/24.
 * description：
 */
@Route(path = RouterActivityPath.Sign.PAGER_LOGIN)
public class LoginActivity extends BaseAppActivity<LoginActivityLoginViewBinding, LoginViewModel> implements TextWatcher, OnRxBusListener {


    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.login_activity_login_view;
    }

    @Override
    public int initVariableId() {
        return BR.loginModel;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //需要判断当前是否是独立控件，如果是独立控件，就需要初始化当前回收状态
        if(BuildConfig.isBuildModule){
            AppStatusManager.getInstance().setAppStatus(AppStatus.STATUS_NORMAL);
        }

        super.onCreate(savedInstanceState);

        StatusBarUtil.setSystemBar(this, getResources().getColor(R.color.transport), false, true);

        viewModel.initListener(this);

        viewModel.activityObservableField.set(this);

        binding.editPhone.addTextChangedListener(this);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                KeyBoardUtils.showKeyboard(binding.editPhone);
            }
        },500);
    }


    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        viewModel.userPhone.set(charSequence.toString());
        viewModel.userPhone.notifyChange();
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        KeyBoardUtils.closeKeybord(binding.editPhone);
    }

    @Override
    public void onRxBusListener(Object obj) {
        EventCenter eventCenter = (EventCenter) obj;
        int code = eventCenter.getEventCode();
        switch (code) {
            case Constants.CLOSE_LOGIN:
                //关闭当前界面,由于ondestroy执行时机需要回收，所以下一个界面的instance初始化比ondestroy的快。导致instance被重置，这里直接手动重置
                finish();
                break;
        }
    }
}
