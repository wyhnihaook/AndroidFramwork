package me.goldze.mvvmhabit.base;

/**
 * @author wengyiheng
 * @date 2020/6/4.
 * description：专门用于ViewModel中返回处理事件
 */
public interface OnNetSuccessCallBack<T> {
    void callBack(T object);
}
