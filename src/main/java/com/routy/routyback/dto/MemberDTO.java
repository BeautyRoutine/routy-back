package com.routy.routyback.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 회원 요청/응답에 쓰는 기본 DTO입니다.
 * 우선 필요한 필드만 두겠습니다. 이후 확장 예정입니다 !
 */
@Getter
@Setter
@ToString
public class MemberDTO {

    @NotBlank
    @Email
    private String email;      // 이메일

    @NotBlank
    private String password;   // 비밀번호 (요청에서만 사용)

    private String nickname;   // 닉네임
    private String skinType;   // 피부 타입(dry, oily 등)
}
