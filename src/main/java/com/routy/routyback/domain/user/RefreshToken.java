package com.routy.routyback.domain.auth;

public class RefreshToken {
    private String userId;
    private String refreshToken;

    public String getUserId() { return userId; }
    public String getRefreshToken() { return refreshToken; }

    public void setUserId(String userId) { this.userId = userId; }
    public void setRefreshToken(String refreshToken) { this.refreshToken = refreshToken; }
}
