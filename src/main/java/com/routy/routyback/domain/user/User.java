package com.routy.routyback.domain.user;

import java.time.LocalDateTime;

public class User {

    private Long userNo;
    private String userId;
    private String userPw;
    private String userName;
    private String userHp;
    private String userEmail;
    private Integer userZip;
    private String userJibunAddr;
    private String userRoadAddr;
    private String userDetailAddr;
    private String userBirth;
    private Integer userLevel;
    private LocalDateTime userRegdate;
    private Integer userStatus;

    // Getters
    public Long getUserNo() {
        return userNo;
    }

    public String getUserId() {
        return userId;
    }

    public String getUserPw() {
        return userPw;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserHp() {
        return userHp;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public Integer getUserZip() {
        return userZip;
    }

    public String getUserJibunAddr() {
        return userJibunAddr;
    }

    public String getUserRoadAddr() {
        return userRoadAddr;
    }

    public String getUserDetailAddr() {
        return userDetailAddr;
    }

    public String getUserBirth() {
        return userBirth;
    }

    public Integer getUserLevel() {
        return userLevel;
    }

    public LocalDateTime getUserRegdate() {
        return userRegdate;
    }

    public Integer getUserStatus() {
        return userStatus;
    }

    // Setters
    public void setUserNo(Long userNo) {
        this.userNo = userNo;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setUserPw(String userPw) {
        this.userPw = userPw;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserHp(String userHp) {
        this.userHp = userHp;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public void setUserZip(Integer userZip) {
        this.userZip = userZip;
    }

    public void setUserJibunAddr(String userJibunAddr) {
        this.userJibunAddr = userJibunAddr;
    }

    public void setUserRoadAddr(String userRoadAddr) {
        this.userRoadAddr = userRoadAddr;
    }

    public void setUserDetailAddr(String userDetailAddr) {
        this.userDetailAddr = userDetailAddr;
    }

    public void setUserBirth(String userBirth) {
        this.userBirth = userBirth;
    }

    public void setUserLevel(Integer userLevel) {
        this.userLevel = userLevel;
    }

    public void setUserRegdate(LocalDateTime userRegdate) {
        this.userRegdate = userRegdate;
    }

    public void setUserStatus(Integer userStatus) {
        this.userStatus = userStatus;
    }
}