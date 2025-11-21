package com.routy.routyback.dto.user;

// 로그인 요청 DTO
public class UserLoginRequest {
    private String userId;
    private String userPw;

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getUserPw() { return userPw; }
    public void setUserPw(String userPw) { this.userPw = userPw; }
}
