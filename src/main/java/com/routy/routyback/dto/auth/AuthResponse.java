package com.routy.routyback.dto.auth;

import com.routy.routyback.domain.user.User;

public class AuthResponse {
    private String token;
    private Long userNo;
    private String userId;
    private String userName;
    private Integer userLevel;
    private Integer userSkin;
    private Long userNo;

    // 생성자
    public AuthResponse(String token, User user) {
        this.token = token;
        this.userNo = user.getUserNo();
        this.userId = user.getUserId();
        this.userName = user.getUserName();
        this.userLevel = user.getUserLevel();
        this.userSkin = user.getUserSkin();
        this.userNo = user.getUserNo(); 
    }

    // Getters
    public String getToken() { return token; }
    public Long getUserNo() { return userNo; }
    public String getUserId() { return userId; }
    public String getUserName() { return userName; }
    public Integer getUserLevel() { return userLevel; }
    public Integer getUserSkin() { return userSkin; }
    public Long getUserNo() { return userNo; }

    // Setters
    public void setToken(String token) { this.token = token; }
    public void setUserNo(Long userNo) { this.userNo = userNo; }
    public void setUserId(String userId) { this.userId = userId; }
    public void setUserName(String userName) { this.userName = userName; }
    public void setUserLevel(Integer userLevel) { this.userLevel = userLevel; }
    public void setUserSkin(Integer userSkin) { this.userSkin = userSkin; }

	public void setUserNo(Long userNo) {
		this.userNo = userNo;
	}

}