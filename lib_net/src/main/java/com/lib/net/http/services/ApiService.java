package com.lib.net.http.services;

import com.lib.net.entity.BaseEntity;
import com.lib.net.entity.LoginEntity;
import com.lib.net.entity.RefreshTokenEntity;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * @author fyf
 * @date 2020/5/19.
 * description：
 */
public interface ApiService {
    /**
     * 获取验证码
     */
    Observable<BaseEntity> getPhoneCode( String phone,long timestamp,String signature);
    /**
     * 刷新登录token
     * @param refreshToken 登陆时保存的refreshToken
     * @param uuid 设备唯一标识码
     */
    Observable<BaseEntity<RefreshTokenEntity>> refreshToken(String refreshToken, String uuid);

    Observable<BaseEntity<LoginEntity>> login(String phone, String code, String uuid,String regId);

    Observable<BaseEntity<String>> logout(String refreshToken);

    Observable<BaseEntity<String>> scanCode(String id,String userId,String timestamp);

}
