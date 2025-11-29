package com.routy.routyback.dto.auth;

public class LoginRequest {
    private String userId;
    private String userPw;

    // Getters
    public String getUserId() { return userId; }
    public String getUserPw() { return userPw; }

    // Setters
    public void setUserId(String userId) { this.userId = userId; }
    public void setUserPw(String userPw) { this.userPw = userPw; }
}