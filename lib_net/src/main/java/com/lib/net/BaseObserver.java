package com.lib.net;


import android.accounts.NetworkErrorException;
import android.app.Activity;
import android.text.TextUtils;


import com.alibaba.android.arouter.launcher.ARouter;
import com.lib.net.entity.BaseEntity;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.net.UnknownServiceException;
import java.util.concurrent.TimeoutException;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import me.goldze.mvvmhabit.base.BaseApplication;
import me.goldze.mvvmhabit.router.path.RouterActivityPath;
import me.goldze.mvvmhabit.utils.ContextHelper;
import me.goldze.mvvmhabit.utils.SPUtils;
import me.goldze.mvvmhabit.utils.ToastUtils;
import retrofit2.HttpException;

/**
 * Created by Administrator on 2018/1/20.
 */

public abstract class BaseObserver<T> implements Observer<T> {
    private static final int ERROR_USER_UNAUTHORD = 10001;
    private static final int ERROR_USER_UNPERMISSION = 10002;
    private static final int ERROR_USER_INVALIDE_TOKEN = 10003;
    protected Activity mActivity;
    private boolean isShowDialog;//是否要显示对话框
    private boolean isCancel; //按返回键能否取消对话框
    private int retryCount;

    public BaseObserver(Activity cxt, boolean isShowDialog, boolean isCancel) {
        this.mActivity = cxt;
        this.isShowDialog = isShowDialog;
        this.isCancel = isCancel;
    }

    public BaseObserver() {

        //默认构建数据
        mActivity = ContextHelper.getLastActivity();
        isShowDialog = true;
        isCancel = true;
    }

    public BaseObserver(Activity cxt, boolean isShowDialog) {
        this.mActivity = cxt;
        this.isShowDialog = isShowDialog;
        this.isCancel = true;
    }

    public BaseObserver(Activity cxt) {
        this.mActivity = cxt;
        this.isShowDialog = true;
        this.isCancel = true;
    }


    protected <T> ObservableTransformer<T, T> setThread() {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> upstream) {
                return upstream.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    @Override
    public void onSubscribe(Disposable d) {
        onRequestStart();
    }

    @Override
    public void onNext(T tBaseEntity) {
        onRequestEnd();
        if (tBaseEntity instanceof BaseEntity) {
            //网络请求返回数据结构
            BaseEntity baseEntity = (BaseEntity) tBaseEntity;

            if (baseEntity.getCode() != 200) {
                //异常提示

                //错误码1001，need login跳转到登录页面
                if (baseEntity.getCode() == 1001) {
                    //路由跳转

                    //退出登录清空数据
                    SPUtils.getInstance().put("token", "");
                    SPUtils.getInstance().put("refreshToken", "");
                    SPUtils.getInstance().put("user_info", "");
                    SPUtils.getInstance().put("userType", "");

                    ARouter.getInstance().build(RouterActivityPath.Sign.PAGER_LOGIN).navigation();
                }


                if (!TextUtils.isEmpty(baseEntity.getMessage())) {
                    //区间不提示
                    if(baseEntity.getCode()>2000&&baseEntity.getCode()<3000){
                        //不提示
                    }
                }



//                ToastUtils.showShort(baseEntity.getMessage());
            } else {
                //获取数据data判断预期结果是否为null，做对应处理，下版本开放，全部页面回归测试
//                Type types = getClass().getGenericSuperclass();
//
//                Type[] genericType = ((ParameterizedType) types).getActualTypeArguments();
//
//                try {
//                    //避免对象新建问题
//                    for (Type t : genericType) {
////                数据结构处理com.iwu.app.base.BaseEntity<com.iwu.app.ui.mine.entry.UserEntity>
//                        String[] typeString = t.toString().split("\\.");
//                        int lastDeleteCount = 1;
//                        if (t.toString().contains("BaseArrayEntity") ||  t.toString().substring(0,t.toString().indexOf("<")).contains("List")) {
//                            //说明是列表页面
//                            lastDeleteCount = 2;
//                        }
//                        String loadFromClassName = t.toString().substring(t.toString().lastIndexOf("<")+1,t.toString().length()-lastDeleteCount);//当前用来加载对象的全路径，获取最后一个<符号开始匹配，获取全路径信息
//
//                        String className = typeString[typeString.length - 1].substring(0, typeString[typeString.length - 1].length() - lastDeleteCount);
//
//                        if (lastDeleteCount == 2) {
//                            className = (t.toString().contains("BaseArrayEntity") ? "BaseArrayEntity" :
//                                    (t.toString().contains("List") ? "List" : "BaseArrayEntity")) + "<" + className + ">";
//                        }
//                        Log.e("wyh", t.toString()+"=当前name="+className+"=当前load路径="+loadFromClassName);
//
//                        //判断是否是列表数据，如果是列表数据，就进行额外处理
//                        if(lastDeleteCount==2){
//
//                            //列表数据，需要额外构建判断对应数据是否为null
//                            if(t.toString().contains("BaseArrayEntity")&&(baseEntity.getData() instanceof BaseArrayEntity)){
//                                //列表处理，直接将数据
//                                if(baseEntity.getData()==null){
//                                    BaseArrayEntity baseArrayEntity = new BaseArrayEntity<>();
//                                    //基本数据构建，构建完毕之后，必要字段处理
//                                    baseArrayEntity.setRows(new ArrayList());
//                                    baseArrayEntity.setTotal(0);
//                                    baseEntity.setData(baseArrayEntity);
//                                }else{
//                                    //存在数据，过滤数据源结构，处理空数据源结构
//                                    DataUtil.setValueRemoveNull(((BaseArrayEntity) (baseEntity.getData())).getRows());
//                                }
//
//                            }else if(t.toString().contains("List")&&(baseEntity.getData() instanceof List)){
//                                //列表处理，直接将数据
//                                if(baseEntity.getData()==null){
//                                    baseEntity.setData(new ArrayList<>());
//                                }else{
//                                    //存在数据，过滤数据源结构，处理空数据源结构
//                                    DataUtil.setValueRemoveNull((List) baseEntity.getData());
//                                }
//                            }
//
//                        }else{
//                            //单个实体类信息处理，需要单个全路径 com.*.*.{}
//
//                            Class clazz = Class.forName(loadFromClassName);
//
//                            //过滤完成之后进行数据重新赋值
//                            baseEntity.setData(DataUtil.setFieldValueNotNull(baseEntity.getData(), clazz));
//                        }
//                    }
//
//
//                } catch (Exception e) {
//
//                    Log.e("wyh","解析实体类null异常："+e.getMessage());
//                }

            }
        }
        onSuccess(tBaseEntity);
    }

    @Override
    public void onError(Throwable e) {
        if (e instanceof HttpException) {
            int code = ((HttpException) e).code();
        } else if (e instanceof UnknownServiceException) {
            //退出登录清空数据
            SPUtils.getInstance().put("token", "");
            SPUtils.getInstance().put("refreshToken", "");
            SPUtils.getInstance().put("user_info", "");
            SPUtils.getInstance().put("userType", "");

            ARouter.getInstance().build(RouterActivityPath.Sign.PAGER_LOGIN).navigation();
        }
        onRequestEnd();
        try {
            if (e instanceof SocketTimeoutException) {
//                ToastUtils.showShortSafe("网络连接超时");
                onFailure(e, false);
            } else if (e instanceof ConnectException
                    || e instanceof TimeoutException
                    || e instanceof NetworkErrorException
                    || e instanceof UnknownHostException || e instanceof IOException || e instanceof NullPointerException) {
                onFailure(e, true);
            } else {
                onFailure(e, false);
            }

            //网络请求过滤对应实体类展现情况，只有全页面被隐藏时才不会进行弹窗提示，其他情况都需要进行弹窗提示
            if (!ignoreToast()) {
                //不忽略的情况，进行弹窗展示
                ToastUtils.showShort(BaseApplication.getInstance().getString(R.string.module_public_network_error));
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    @Override
    public void onComplete() {
        onRequestEnd();
    }

    /**
     * 返回成功
     *
     * @param t
     */
    protected abstract void onSuccess(T t);


    /**
     * 网络异常捕获忽略弹窗提示
     */
    protected boolean ignoreToast() {
        boolean isIgnore = false;//默认不忽略
        //这里获得到的是类的泛型的类型
        Type types = getClass().getGenericSuperclass();

        Type[] genericType = ((ParameterizedType) types).getActualTypeArguments();
        for (Type t : genericType) {
//                数据结构处理com.iwu.app.base.BaseEntity<com.iwu.app.ui.mine.entry.UserEntity>

            String[] typeString = t.toString().split("\\.");
            int lastDeleteCount = 1;
            if (t.toString().contains("BaseArrayEntity") || t.toString().substring(0,t.toString().indexOf("<")).contains("List")) {
                //说明是列表页面
                lastDeleteCount = 2;
            }
            String className = typeString[typeString.length - 1].substring(0, typeString[typeString.length - 1].length() - lastDeleteCount);

            if (lastDeleteCount == 2) {
                className = (t.toString().contains("BaseArrayEntity") ? "BaseArrayEntity" :
                        (t.toString().contains("List") ? "List" : "BaseArrayEntity")) + "<" + className + ">";
            }

            isIgnore = true;
        }

        return isIgnore;
    }

    /**
     * 返回失败
     *
     * @param e
     * @param isNetWorkError 是否是网络错误
     */
    protected abstract void onFailure(Throwable e, boolean isNetWorkError);

    protected void onRequestStart() {
        try {

            boolean isIgnore = false;//默认不忽略
            //这里获得到的是类的泛型的类型
            Type types = getClass().getGenericSuperclass();

            Type[] genericType = ((ParameterizedType) types).getActualTypeArguments();
            for (Type t : genericType) {
//                数据结构处理com.iwu.app.base.BaseEntity<com.iwu.app.ui.mine.entry.UserEntity>

                String[] typeString = t.toString().split("\\.");
                int lastDeleteCount = 1;
                if (t.toString().contains("BaseArrayEntity") || t.toString().substring(0,t.toString().indexOf("<")).contains("List")) {
                    //说明是列表页面
                    lastDeleteCount = 2;
                }
                String className = typeString[typeString.length - 1].substring(0, typeString[typeString.length - 1].length() - lastDeleteCount);

                if (lastDeleteCount == 2) {
                    className = (t.toString().contains("BaseArrayEntity") ? "BaseArrayEntity" :
                            (t.toString().contains("List") ? "List" : "BaseArrayEntity")) + "<" + className + ">";
                }

                isIgnore = true;
            }


            if (isShowDialog && mActivity != null && !mActivity.isFinishing()) {
                //针对部分接口不刷新loading状态
                if (isIgnore) {

                } else {
//                    showProgressDialog();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void onRequestEnd() {
        if (mActivity == null || mActivity.isFinishing())
            return;
        try {
//            closeProgressDialog();
        } catch (Exception e) {
        }

    }


}

