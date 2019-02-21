package com.thunder.as31try.bean;

/**
 * Created by qian on 18-1-24.
 */

public class MiTokenInfo {
    public String token;
    public String refreshToken;
    public int expireLiveTime;
    public long tokenStartTime;

    public String getToken() {
        return token == null ? "" : token;
    }

    public void setToken(String token) {
        this.token = token == null ? "" : token;
    }

    public String getRefreshToken() {
        return refreshToken == null ? "" : refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken == null ? "" : refreshToken;
    }

    public int getExpireLiveTime() {
        return expireLiveTime;
    }

    public void setExpireLiveTime(int expireLiveTime) {
        this.expireLiveTime = expireLiveTime;
    }

    public long getTokenStartTime() {
        return tokenStartTime;
    }

    public void setTokenStartTime(long tokenStartTime) {
        this.tokenStartTime = tokenStartTime;
    }

    @Override
    public String toString() {
        return "MiTokenInfo{" +
                "token='" + token + '\'' +
                ", refreshToken='" + refreshToken + '\'' +
                ", expireLiveTime=" + expireLiveTime +
                ", tokenStartTime=" + tokenStartTime +
                '}';
    }
}
