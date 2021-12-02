package com.lib.login.viewmodel;

import android.app.Application;
import android.os.Bundle;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;

import com.lib.login.CodeActivity;
import com.lib.login.util.VerifyUtil;
import com.lib.net.BaseObserver;
import com.lib.net.base.NetApplication;
import com.lib.net.entity.BaseEntity;

import java.util.HashMap;
import java.util.Map;

import me.goldze.mvvmhabit.base.BaseActivity;
import me.goldze.mvvmhabit.base.BaseRxViewModel;
import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.utils.RegexUtils;
import me.goldze.mvvmhabit.utils.SPUtils;
import me.goldze.mvvmhabit.utils.ToastUtils;

/**
 * @author wengyiheng
 * @date 2021/8/24.
 * description：
 */
public class LoginViewModel extends BaseRxViewModel {

    public ObservableField<String> userPhone = new ObservableField<>("");

    public ObservableField<BaseActivity> activityObservableField=new ObservableField<>();


    public LoginViewModel(@NonNull Application application) {
        super(application);
    }


    public BindingCommand login = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            if (TextUtils.isEmpty(userPhone.get())) {
                //造成不可点击的现象
                return;
            }
            if (!RegexUtils.isMobileSimple(userPhone.get())) {
                ToastUtils.showShort("请输入正确手机号！");
                return;
            }
            Map<String,String> params=new HashMap<>();
            long time=System.currentTimeMillis();
            params.put("phone", userPhone.get());
            params.put("timestamp",time+"");

            sendCode(userPhone.get(),time, VerifyUtil.md5Hex(params));

        }
    });

    private void sendCode(String phone,long timestamp,String signature) {

        NetApplication.getIns().getApiService().getPhoneCode(phone,timestamp,signature)
                .compose(activityObservableField.get().bindToLifecycle())
                .subscribe(new BaseObserver<BaseEntity>() {
                    @Override
                    protected void onSuccess(BaseEntity baseEntity) {
//                        ToastUtils.showShortSafe(baseEntity.getMessage());
                        if(baseEntity.getCode()==200){
                            SPUtils.getInstance().put("phone", phone);
                            Bundle mBundle = new Bundle();
                            mBundle.putString("phone", phone);

                            startActivity(CodeActivity.class, mBundle);

                        }
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) {

                    }
                });
    }
}
