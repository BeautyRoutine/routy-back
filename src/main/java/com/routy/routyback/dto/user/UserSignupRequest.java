package com.routy.routyback.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 프론트에서 오는 회원가입 요청 DTO
 * 이메일 / 비밀번호 / 닉네임 정도만 받는 용도
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserSignupRequest {

    private String email;
    private String password;
    private String nickname;
}
