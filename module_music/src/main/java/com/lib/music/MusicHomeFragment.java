package com.lib.music;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.lib.music.viewmodel.MusicHomeViewModel;
import com.lib.music.BR;

import com.lib.music.databinding.MusicFragmentHomeBinding;


import io.reactivex.annotations.Nullable;
import me.goldze.mvvmhabit.base.BaseFragment;
import me.goldze.mvvmhabit.router.path.RouterFragmentPath;

/**
 * @author wengyiheng
 * @date 2021/9/17.
 * description：
 */
@Route(path = RouterFragmentPath.Music.PAGER_MUSIC)
public class MusicHomeFragment extends BaseFragment<MusicFragmentHomeBinding, MusicHomeViewModel> {

    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.music_fragment_home;
    }

    @Override
    public int initVariableId() {
        return BR.musicHomeViewModel;
    }

    @Override
    public void initData() {
        super.initData();
    }
}
