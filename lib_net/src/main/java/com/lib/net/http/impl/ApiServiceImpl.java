package com.lib.net.http.impl;


import com.lib.net.RRetrofit;
import com.lib.net.entity.BaseEntity;
import com.lib.net.entity.LoginEntity;
import com.lib.net.entity.RefreshTokenEntity;
import com.lib.net.http.api.ApiProvider;
import com.lib.net.http.services.ApiService;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


/**
 * @author wengyiheng
 * @date 2020/6/18.
 * description：
 */
public class ApiServiceImpl extends RRetrofit implements ApiService {

    @Override
    public Observable<BaseEntity<RefreshTokenEntity>> refreshToken(String refreshToken, String uuid) {
        return getApiService(ApiProvider.class).refreshToken(getTransBody().put("refreshToken",refreshToken).put("uuid",uuid)).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<BaseEntity<LoginEntity>> login(String phone, String code, String uuid,String regId) {
        return getApiService(ApiProvider.class).login(getTransBody().put("phone",phone).put("code",code).put("uuid",uuid).put("regId",regId)).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<BaseEntity<String>> logout(String refreshToken) {
        return getApiService(ApiProvider.class).logout(getTransBody().put("refreshToken",refreshToken)).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<BaseEntity<String>> scanCode(String id, String userId, String timestamp) {
        return getApiService(ApiProvider.class).scanCode(getTransBody().put("id",id)
                .put("userId",userId)
                .put("timestamp",timestamp)).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

    }

    @Override
    public Observable<BaseEntity> getPhoneCode(String phone,long timestamp,String signature) {
        return getApiService(ApiProvider.class).getPhoneCode(getTransBody().put("phone",phone).put("timestamp",timestamp).put("signature",signature)).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
