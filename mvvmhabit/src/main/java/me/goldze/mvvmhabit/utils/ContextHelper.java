package me.goldze.mvvmhabit.utils;

import android.util.SparseArray;

import java.lang.ref.WeakReference;

import me.goldze.mvvmhabit.base.BaseActivity;

/**
 * @author wengyiheng
 * @date 2020/7/15.
 * description：用于存储Activity,控制dialog状态
 */
public class ContextHelper {

    private static SparseArray<WeakReference<BaseActivity>> mAct = new SparseArray<>();
    private static WeakReference<BaseActivity> mLast = null;

    /**
     * 添加act
     * @param act
     * @param <T>
     */
    public static <T extends BaseActivity> void addActivity(T act) {
        int code = act.getClass().hashCode();
        mAct.put(code, new WeakReference<BaseActivity>(act));
    }

    /**
     * 设置最后的
     * @param act
     */
    public static void setLastActivity(BaseActivity act) {
        if (mLast != null && mLast.get() == act) return;
        mLast = new WeakReference<>(act);
    }

    /**
     * 获取act
     * @param tClass
     * @param <T>
     * @return
     */
    public static <T extends BaseActivity> T getActivity(Class<T> tClass) {
        WeakReference<BaseActivity> ref = mAct.get(tClass.hashCode());
        return ref != null ? (T) ref.get() : null;
    }

    /**
     * 移除Act
     * @param tClass
     * @param <T>
     */
    public static <T extends BaseActivity> void removeActivity(Class<T> tClass) {
        mAct.remove(tClass.hashCode());
    }

    /**
     * 最后一个Activity
     * @return
     */
    public static BaseActivity getLastActivity() {
        if (mLast == null) return null;
        return mLast.get();
    }

}