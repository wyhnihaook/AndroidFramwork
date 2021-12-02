package com.lib.music;

import android.app.Application;

import me.goldze.mvvmhabit.router.module.IModuleInit;
import me.goldze.mvvmhabit.utils.KLog;

/**
 * Created by goldze on 2018/6/21 0021.
 */

public class MusicModuleInit implements IModuleInit {
    @Override
    public boolean onInitAhead(Application application) {
        KLog.e("首页模块初始化 -- onInitAhead");
        return false;
    }

    @Override
    public boolean onInitLow(Application application) {
        KLog.e("首页模块初始化 -- onInitLow");
        return false;
    }
}
