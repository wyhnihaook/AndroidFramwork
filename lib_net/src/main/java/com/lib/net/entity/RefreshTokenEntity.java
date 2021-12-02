package com.lib.net.entity;

import java.util.List;

/**
 * @author wengyiheng
 * @date 2020/6/1.
 * description：登录成功返回相关数据，其中token要保存在本地，用于后续数据请求
 */
public class RefreshTokenEntity {
    public String token;

    public String refreshToken;

    public boolean isCompletion;//是否完善过个人信息

    public List<String> roleTypes;

    public boolean isCompletion() {
        return isCompletion;
    }

    public void setCompletion(boolean completion) {
        isCompletion = completion;
    }

    public List<String> getRoleTypes() {
        return roleTypes;
    }

    public void setRoleTypes(List<String> roleTypes) {
        this.roleTypes = roleTypes;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
