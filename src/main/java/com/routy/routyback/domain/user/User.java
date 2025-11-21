package com.routy.routyback.domain.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * USERS 테이블과 매핑되는 기본 사용자 도메인.
 * 로그인/회원가입/카카오 로그인에서 공통으로 사용한다.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    // USERS.USERNO (PK, IDENTITY)
    private Long userNo;

    // USERS.USERID - 로그인 아이디(이메일 포함)
    private String userId;

    // USERS.USERPW - 비밀번호(로컬 회원용, 카카오는 null 가능)
    private String userPw;

    // USERS.USERNAME - 실명
    private String userName;

    // USERS.USERNICK - 닉네임(필요 없으면 사용 안 해도 됨)
    private String userNick;

    // USERS.USEREMAIL - 이메일
    private String userEmail;

    // USERS.USERPHONE - 휴대폰 번호
    private String userPhone;

    // USERS.USERAUTH - 권한(ROLE_USER, ROLE_ADMIN 등)
    private String userAuth;

    // USERS.USERSTATUS - 계정 상태 (1:정상, 0:탈퇴/중지 등)
    private Integer userStatus;
}
