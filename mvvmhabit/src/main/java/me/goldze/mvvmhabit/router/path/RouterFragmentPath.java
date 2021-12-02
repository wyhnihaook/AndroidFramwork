package me.goldze.mvvmhabit.router.path;

/**
 * 用于组件开发中，ARouter多Fragment跳转的统一路径注册
 * 在这里注册添加路由路径，需要清楚的写好注释，标明功能界面
 * Created by goldze on 2018/6/21
 */

public class RouterFragmentPath {
    /**
     * 场次组件
     */
    public static class Sessions {
        private static final String SESSIONS = "/Sessions";
        /*场次首页*/
        public static final String PAGER_SESSIONS = SESSIONS + "/Home";
    }

    /**
     * 赛程组件
     */
    public static class Schedule {
        private static final String SCHEDULE = "/Schedule";
        /*赛程首页*/
        public static final String PAGER_SCHEDULE = SCHEDULE + "/Home";
    }

    /**
     * 音乐组件
     */
    public static class Music {
        private static final String MUSIC = "/Music";
        /*音乐首页*/
        public static final String PAGER_MUSIC= MUSIC + "/Home";
    }

    /**
     * 我的组件
     */
    public static class User {
        private static final String USER = "/User";
        /*我的首页*/
        public static final String PAGER_USER = USER + "/Home";
    }

}
