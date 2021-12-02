package com.lib.login;

import android.app.Application;

import me.goldze.mvvmhabit.router.module.IModuleInit;
import me.goldze.mvvmhabit.utils.KLog;

/**
 * @author wengyiheng
 * @date 2021/8/24.
 * description：
 */
public class LoginModuleInit implements IModuleInit {
    @Override
    public boolean onInitAhead(Application application) {
        KLog.e("主业务模块初始化 -- onInitAhead");
        return false;
    }

    @Override
    public boolean onInitLow(Application application) {
        KLog.e("主业务模块初始化 -- onInitLow");
        return false;
    }
}
