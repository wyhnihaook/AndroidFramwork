package com.lib.net.base;

import com.lib.net.ServiceProxy;
import com.lib.net.http.impl.ApiServiceImpl;
import com.lib.net.http.services.ApiService;

import java.util.HashMap;
import java.util.Map;

import me.goldze.mvvmhabit.base.BaseApplication;

/**
 * @author wengyiheng
 * @date 2021/8/24.
 * description：
 */
public class NetApplication extends BaseApplication {

    private static NetApplication _ins;

    public static NetApplication getIns() {
        return _ins;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        _ins = this;

        loadService();//初始化网络请求实例
        initServices();//网络请求示例提供
    }


    /**
     * 获取网络请求实例，保存在缓存中，通过类型获得服务,避免多次新建重复对象
     **/
    private Map<String, Object> _caches = new HashMap<>();

    public <T> T getService(Class<T> serviceClass) {
        String name = serviceClass.getSimpleName();
        String key = name.substring(0, 1).toLowerCase() + name.substring(1, name.length());
        return (T) _caches.get(key);
    }

    public void loadService() {
        Class[] classNames = new Class[]{
                ApiServiceImpl.class,
        };
        for (Class clazz : classNames) {
            String name = clazz.getSimpleName();
            String key = name.substring(0, 1).toLowerCase() + name.substring(1, name.length() - 4);
            try {
                _caches.put(key, new ServiceProxy(clazz.newInstance()).getProxy());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * ================================网络信息接口初始化=============
     */
    protected ApiService apiService;


    public ApiService getApiService() {
        return apiService;
    }


    private void initServices() {
        apiService = getService(ApiService.class);

    }

}
