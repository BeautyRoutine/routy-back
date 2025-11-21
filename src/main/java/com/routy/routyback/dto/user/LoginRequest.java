package com.routy.routyback.dto.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 기본 로그인 요청 DTO
 */
@Getter
@Setter
@NoArgsConstructor
public class LoginRequest {

    // 아이디(이메일)로 로그인한다고 가정
    private String userId;

    private String password;
}
