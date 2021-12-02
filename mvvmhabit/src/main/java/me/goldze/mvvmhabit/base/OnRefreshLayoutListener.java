package me.goldze.mvvmhabit.base;

/**
 * @author wengyiheng
 * @date 2020/6/2.
 * description：网络请求结果对列表状态的更新监听
 */
public interface OnRefreshLayoutListener {
    /**
     * 网络请求后回调进行能否上拉加载的设置
     *
     * @param isRefresh 判断当前加载状态，用于回弹布局
     * @param canLoadMore true:存在数据，能进行上拉加载  false:不存在数据
     * */
    void onLayoutStatusListener(boolean isRefresh, boolean canLoadMore);
}
