package com.routy.routyback.dto.auth;

import com.routy.routyback.domain.user.User;

public class AuthResponse {
    private String token;
    private String userId;
    private String userName;
    private Integer userLevel;

    // 생성자
    public AuthResponse(String token, User user) {
        this.token = token;
        this.userId = user.getUserId();
        this.userName = user.getUserName();
        this.userLevel = user.getUserLevel();
    }

    // Getters
    public String getToken() { return token; }
    public String getUserId() { return userId; }
    public String getUserName() { return userName; }
    public Integer getUserLevel() { return userLevel; }

    // Setters
    public void setToken(String token) { this.token = token; }
    public void setUserId(String userId) { this.userId = userId; }
    public void setUserName(String userName) { this.userName = userName; }
    public void setUserLevel(Integer userLevel) { this.userLevel = userLevel; }
}