package me.goldze.mvvmhabit.router.config;

/**
 * Created by goldze on 2018/6/21 0021.
 * 组件生命周期反射类名管理，在这里注册需要初始化的组件，通过反射动态调用各个组件的初始化方法
 * 注意：以下模块中初始化的Module类不能被混淆
 */

public class ModuleLifecycleReflexs {
    private static final String BaseInit = "me.goldze.mvvmhabit.router.module.BaseModuleInit";


    //主业务模块
    private static final String MainInit = "com.lib.main.MainModuleInit";

    //身份验证模块
    private static final String LoginInit = "com.lib.login.LoginModuleInit";

    //场次信息
    private static final String SessionsInit = "com.lib.sessions.SessionsModuleInit";

    //赛程信息
    private static final String ScheduleInit = "com.lib.schedule.ScheduleModuleInit";

    //音乐信息
    private static final String MusicInit = "com.lib.music.MusicModuleInit";

    //我的信息
    private static final String UserInit = "com.lib.user.UserModuleInit";

    public static String[] initModuleNames = {BaseInit,LoginInit,MainInit,SessionsInit,ScheduleInit,MusicInit,UserInit};
}
