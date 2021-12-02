package me.goldze.mvvmhabit.base;

/**
 * @author wengyiheng
 * @date 2020/6/19.
 * description：custom dialog中的点击返回
 */
public interface OnDialogCallBackListener<T> {
    void onCancelListener();
    void onSubmitListener(T t);
}
