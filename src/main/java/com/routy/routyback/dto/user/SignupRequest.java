package com.routy.routyback.dto.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 회원가입 요청 DTO
 * 필요한 필드만 사용하고 나머지는 DB에서 기본값 사용
 */
@Getter
@Setter
@NoArgsConstructor
public class SignupRequest {

    private String userId;      // 로그인 아이디
    private String password;    // 비밀번호
    private String userName;    // 이름
    private String userEmail;   // 이메일
    private String userPhone;   // 휴대폰 (선택)
}
