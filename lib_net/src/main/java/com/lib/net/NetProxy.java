package com.lib.net;


import android.accounts.NetworkErrorException;
import android.util.Log;


import com.lib.net.base.NetApplication;
import com.lib.net.entity.BaseEntity;
import com.lib.net.entity.RefreshTokenEntity;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.UnknownServiceException;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import me.goldze.mvvmhabit.base.BaseApplication;
import me.goldze.mvvmhabit.http.NetworkUtil;
import me.goldze.mvvmhabit.utils.SPUtils;
import retrofit2.Retrofit;

/**
 * @author wengyiheng
 * @date 2020/6/18.
 * description：处理接口请求额外处理：例如：token失效需要通过refreshtoken去更新接口激活token
 */
public class NetProxy implements InvocationHandler {

    private static final String TAG = "NetProxy";
    private Retrofit retrofit;
    private Object target;

    public NetProxy(Retrofit retrofit) {
        this.retrofit = retrofit;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        try {
            final Observable<Object> ob = (Observable<Object>) method.invoke(target, args);

            if (!NetworkUtil.isNetworkAvailable(BaseApplication.getInstance())) {
                Log.d(TAG, "the network error");
                //直接抛出界面无网络信息
//                ToastUtils.showShort(IWuApplication.getIns().getString(R.string.network_error));
                return ob.map(new Function<Object, Object>() {
                    @Override
                    public Object apply(Object obj) throws Exception {
                        throw new NetworkErrorException();
                    }
                });
            } else {
                return ob.map(new Function<Object, Object>() {
                    @Override
                    public Object apply(Object obj) {
                        if (obj instanceof BaseEntity) {
                            BaseEntity baseModel = (BaseEntity) obj;
                            if (baseModel != null && baseModel.getCode() == 1000) {//token失效，需要通过refreshToken获取最新token，并重构网络请求

                                throw new UnsupportedOperationException("HackReLogin");
                            }
                        }
                        return obj;
                    }
                }).retryWhen(new Function<Observable<? extends Throwable>, Observable<?>>() {
                    @Override
                    public Observable<?> apply(Observable<? extends Throwable> errors) {
                        return errors.zipWith(Observable.range(1, 3), new BiFunction<Throwable, Integer, Object>() {
                            @Override
                            public Object apply(Throwable throwable, Integer i) {
                                if (throwable instanceof UnsupportedOperationException) {
                                    Log.d(TAG, "retry count is:" + i);
                                    return i;
                                } else {
                                    return throwable;
                                }
                            }
                        })//返回的如果是token失效就要重新请求接口
                        .flatMap(new Function<Object, Observable<?>>() {//在2^i秒中之后重新尝试接口
                            @Override
                            public Observable<? > apply(Object retryCount) {
                                if (retryCount instanceof Integer) {
                                    //请求接口进行刷新操作
                                    return NetApplication.getIns()
                                            .getApiService()
                                            .refreshToken(SPUtils.getInstance().getString("refreshToken"),"");

                                } else if (retryCount instanceof Throwable){
                                    return Observable.error((Throwable) retryCount);
                                } else {
                                    return Observable.never();
                                }
                            }
                        }).flatMap(new Function<Object, ObservableSource<?>>() {
                                    @Override
                                    public ObservableSource<?> apply(Object o) throws Exception {
                                        if(o instanceof BaseEntity){
                                            //refreshToken之后需要进行1001重新登录判断

                                            //说明刷新过token了继续请求
                                            BaseEntity<RefreshTokenEntity> baseEntity = (BaseEntity<RefreshTokenEntity>) o;
                                            if(baseEntity!=null&&baseEntity.getCode() == 1001){

                                                //返回当前的异常信息，不进行二次请求
                                                throw new UnknownServiceException("ReLogin");

                                            }else{
                                                RefreshTokenEntity loginEntity=baseEntity.getData();
                                                if(loginEntity!=null){
                                                    SPUtils.getInstance().put("token",loginEntity.getToken());
                                                    SPUtils.getInstance().put("refreshToken",loginEntity.getRefreshToken());

                                                    RRetrofit.reCreateHttpClient();
                                                }
                                                //一秒直接刷新
                                                return Observable.timer((long) Math.pow(1,1), TimeUnit.SECONDS);
                                            }
                                        }
                                        return Observable.never();
                                    }
                                });
                    }
                }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("NetProxy", "invoke error");
            return method.invoke(target, args);
        }
    }

    public <T> T getProxy(Class<T> clazz) {
        target = retrofit.create(clazz);
        T proxy = (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class<?>[]{clazz}, this);
        return proxy;
    }

}