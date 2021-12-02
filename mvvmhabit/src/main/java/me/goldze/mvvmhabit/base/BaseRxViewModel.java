package me.goldze.mvvmhabit.base;

import android.app.Application;

import androidx.annotation.NonNull;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import me.goldze.mvvmhabit.bus.RxBus;
import me.goldze.mvvmhabit.bus.RxSubscriptions;
import me.goldze.mvvmhabit.utils.EventCenter;

/**
 * @author wengyiheng
 * @date 2021/8/25.
 * description：
 */
public class BaseRxViewModel extends BaseViewModel {

    public BaseRxViewModel(@NonNull Application application) {
        super(application);
    }

    public OnRxBusListener onRxBusListener;

    public void initListener(OnRxBusListener onRxBusListener){
        this.onRxBusListener=onRxBusListener;
    }

    /**注册事件接收者*/
    //订阅者
    private Disposable mSubscription;
    //注册RxBus
    @Override
    public void registerRxBus() {
        super.registerRxBus();
        mSubscription = RxBus.getDefault().toObservable(EventCenter.class)
                .subscribe(new Consumer<EventCenter>() {
                    @Override
                    public void accept(EventCenter s) throws Exception {
                        //回调到detail中信息
                        if(onRxBusListener!=null){
                            onRxBusListener.onRxBusListener(s);
                        }
                    }
                });
        //将订阅者加入管理站
        RxSubscriptions.add(mSubscription);
    }

    //移除RxBus
    @Override
    public void removeRxBus() {
        super.removeRxBus();
        //将订阅者从管理站中移除
        RxSubscriptions.remove(mSubscription);
    }
}
