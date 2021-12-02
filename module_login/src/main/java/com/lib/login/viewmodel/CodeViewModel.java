package com.lib.login.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableList;

import com.alibaba.android.arouter.launcher.ARouter;
import com.lib.net.BaseObserver;
import com.lib.net.base.NetApplication;
import com.lib.net.entity.BaseEntity;
import com.lib.net.entity.LoginEntity;

import me.goldze.mvvmhabit.base.BaseActivity;
import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.bus.RxBus;
import me.goldze.mvvmhabit.router.path.RouterActivityPath;
import me.goldze.mvvmhabit.utils.Constants;
import me.goldze.mvvmhabit.utils.EventCenter;
import me.goldze.mvvmhabit.utils.SPUtils;
import me.goldze.mvvmhabit.utils.ToastUtils;

/**
 * @author wengyiheng
 * @date 2021/8/25.
 * description：
 */
public class CodeViewModel  extends BaseViewModel {

    public ObservableField textCode1 = new ObservableField<>("");
    public ObservableField textCode2 = new ObservableField<>("");
    public ObservableField textCode3= new ObservableField<>("");
    public ObservableField textCode4 = new ObservableField<>("");
    public ObservableField<String> editCode = new ObservableField("");

    public ObservableList<String> codes= new ObservableArrayList<>();

    public ObservableField<String> phoneInfo = new ObservableField<String>("");

    //用于管理网络请求的生命周期
    public ObservableField<BaseActivity> activityObservableField=new ObservableField<>();



    public CodeViewModel(@NonNull Application application) {
        super(application);
    }

    public BindingCommand login  = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            String code = textCode1.get().toString()+textCode2.get().toString()+textCode3.get().toString()+textCode4.get().toString();

            if(code.length()<4){
                return;
            }
            NetApplication.getIns()
                    .getApiService()
                    .login(phoneInfo.get() ,code ,"","")
                    .compose(activityObservableField.get().bindToLifecycle())
                    .subscribe(new BaseObserver<BaseEntity<LoginEntity>>() {
                        @Override
                        protected void onSuccess(BaseEntity<LoginEntity> baseEntity) {
                            if(baseEntity.getCode()==200){
                                LoginEntity loginEntity=baseEntity.getData();
                                if(loginEntity!=null){
                                    SPUtils.getInstance().put("token",loginEntity.getToken());
                                    SPUtils.getInstance().put("phone",phoneInfo.get());
                                    SPUtils.getInstance().put("refreshToken",loginEntity.getRefreshToken());
                                }

                                RxBus.getDefault().post(new EventCenter(Constants.CLOSE_LOGIN));

                                //跳转到首页页面
                                ARouter.getInstance().build(RouterActivityPath.Main.PAGER_MAIN).navigation();

//                                finish();
                            }
                        }

                        @Override
                        protected void onFailure(Throwable e, boolean isNetWorkError) {

                        }
                    });

        }
    });



    public BindingCommand getCode = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            ToastUtils.showShortSafe("获取验证码");
        }
    });


}
