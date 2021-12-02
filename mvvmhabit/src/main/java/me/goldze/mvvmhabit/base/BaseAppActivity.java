package me.goldze.mvvmhabit.base;

import androidx.databinding.ViewDataBinding;

import me.goldze.mvvmhabit.utils.recovery.AppStatus;
import me.goldze.mvvmhabit.utils.recovery.AppStatusManager;

/**
 * @author wengyiheng
 * @date 2021/8/24.
 * description：
 */
public abstract class BaseAppActivity  <V extends ViewDataBinding, VM extends BaseViewModel> extends BaseActivity<V,VM> {

    @Override
    public boolean initRecycle() {
        //判断进程被回收之后进行重启
        if (AppStatusManager.getInstance().getAppStatus() == AppStatus.STATUS_RECYCLE){

            //跳转到首页

            return true;
        }
        return super.initRecycle();
    }

}
