package com.lib.net.http.api;


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
public interface ApiProvider {

    /**
     * 刷新登录token
     */
    @FormUrlEncoded
    @POST("/api/login/refreshToken")
    Observable<BaseEntity<RefreshTokenEntity>> refreshToken(@FieldMap Map<String, Object> map);
    /**
     * 获取验证码
     */
    @FormUrlEncoded
    @POST("/api/competition/scan/getPhoneCode")
    Observable<BaseEntity> getPhoneCode(@FieldMap Map<String, Object> map);

    /**
     * 微信登录判断是否绑定过手机号
     */
    @FormUrlEncoded
    @POST("/api/competition/scan/doLoginByPhoneAndCode")
    Observable<BaseEntity<LoginEntity>> login(@FieldMap Map<String, Object> map);

    /**
     * 退出登录
     */
    @FormUrlEncoded
    @POST("/api/competition/scan/logout")
    Observable<BaseEntity<String>> logout(@FieldMap Map<String, Object> map);

    /**
     * 扫码结果
     */
    @FormUrlEncoded
    @POST("/api/competition/scan/scanCode")
    Observable<BaseEntity<String>> scanCode(@FieldMap Map<String, Object> map);


}
