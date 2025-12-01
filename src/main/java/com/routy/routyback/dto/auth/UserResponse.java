package com.routy.routyback.dto.auth;

public class UserResponse {

    private final Long userNo;
    private final String email;
    private final String name;
    private final String token;

    public UserResponse(Long userNo, String email, String name, String token) {
        this.userNo = userNo;
        this.email = email;
        this.name = name;
        this.token = token;
    }

    public Long getUserNo() {
        return userNo;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getToken() {
        return token;
    }
}
