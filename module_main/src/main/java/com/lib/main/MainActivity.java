package com.lib.main;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.lib.main.BR;
import com.lib.main.R;
import com.lib.main.databinding.MainActivityMainBinding;
import com.lib.main.viewmodel.MainViewModel;


import java.util.ArrayList;
import java.util.List;

import me.goldze.mvvmhabit.base.BaseAppActivity;
import me.goldze.mvvmhabit.router.path.RouterActivityPath;
import me.goldze.mvvmhabit.router.path.RouterFragmentPath;
import me.goldze.mvvmhabit.utils.StatusBarUtil;
import me.goldze.mvvmhabit.utils.recovery.AppStatus;
import me.goldze.mvvmhabit.utils.recovery.AppStatusManager;
import me.majiajie.pagerbottomtabstrip.NavigationController;
import me.majiajie.pagerbottomtabstrip.item.NormalItemView;
import me.majiajie.pagerbottomtabstrip.listener.OnTabItemSelectedListener;

/**
 * @author wengyiheng
 * @date 2021/9/17.
 * description：首页信息
 */

@Route(path = RouterActivityPath.Main.PAGER_MAIN)
public class MainActivity extends BaseAppActivity<MainActivityMainBinding, MainViewModel> {

    private List<Fragment> mFragments;

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.main_activity_main;
    }

    @Override
    public int initVariableId() {
        return BR.mainViewModel;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //需要判断当前是否是独立控件，如果是独立控件，就需要初始化当前回收状态
        if(BuildConfig.isBuildModule){
            AppStatusManager.getInstance().setAppStatus(AppStatus.STATUS_NORMAL);
        }

        super.onCreate(savedInstanceState);

        StatusBarUtil.setSystemBar(this, getResources().getColor(R.color.transport), false, true);

    }

    @Override
    public void initData() {
        super.initData();
        //初始化Fragment
        initFragment();
        //初始化底部Button
        initBottomTab();
    }

    private void initFragment() {
        Fragment sessionsFragment = (Fragment) ARouter.getInstance().build(RouterFragmentPath.Sessions.PAGER_SESSIONS).navigation();
        Fragment scheduleFragment = (Fragment) ARouter.getInstance().build(RouterFragmentPath.Schedule.PAGER_SCHEDULE).navigation();
        Fragment musicFragment = (Fragment) ARouter.getInstance().build(RouterFragmentPath.Music.PAGER_MUSIC).navigation();
        Fragment meFragment = (Fragment) ARouter.getInstance().build(RouterFragmentPath.User.PAGER_USER).navigation();
        mFragments = new ArrayList<>();
        mFragments.add(sessionsFragment);
        mFragments.add(scheduleFragment);
        mFragments.add(musicFragment);
        mFragments.add(meFragment);
        if (sessionsFragment != null) {
            //默认选中第一个
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.frameLayout, sessionsFragment);
            transaction.commitAllowingStateLoss();
        }
    }


    private void initBottomTab() {
        NavigationController navigationController = binding.pagerBottomTab.custom()
                .addItem(newBottomItem(R.mipmap.ic_dialog_back_logo,R.mipmap.ic_exception,"场次"))
                .addItem(newBottomItem(R.mipmap.ic_dialog_back_logo,R.mipmap.ic_exception,"赛程"))
                .addItem(newBottomItem(R.mipmap.ic_dialog_back_logo,R.mipmap.ic_exception,"音乐"))
                .addItem(newBottomItem(R.mipmap.ic_dialog_back_logo,R.mipmap.ic_exception,"我的"))
                .build();

        //设置消息数
//        navigationController.setMessageNumber(2, 11);
        //设置显示小圆点
//        navigationController.setHasMessage(0, true);

        //底部按钮的点击事件监听
        navigationController.addTabItemSelectedListener(new OnTabItemSelectedListener() {
            @Override
            public void onSelected(int index, int old) {
                Fragment currentFragment = mFragments.get(index);
                if (currentFragment != null) {
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.frameLayout, currentFragment);
                    transaction.commitAllowingStateLoss();
                }
            }

            @Override
            public void onRepeat(int index) {
            }
        });


        //默认选中
//        navigationController.setSelect(1);
    }


    /**
     * 底部item信息初始化
     * */
    private NormalItemView newBottomItem(int normalDrawable , int checkDrawable,String text){
        NormalItemView normalItemView =new NormalItemView(this);
        normalItemView.initialize(normalDrawable, checkDrawable, text);
        normalItemView.setTextDefaultColor(Color.GRAY);
        normalItemView.setTextCheckedColor(Color.RED);
        return normalItemView;
    }
}
