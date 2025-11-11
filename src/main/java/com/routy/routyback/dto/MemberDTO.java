package com.routy.routyback.dto;

import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberDTO {

    // PK
    private Long userNo;

    // 계정 기본
    private String userId;
    private String userEmail;
    private String userNick;
    private String userPw;
    private String userName;
    private String userHp;

    // 인증/식별
    private String userAuth;   // 오류나면 말해주세여 ! -> VARCHAR2 사용으로 인한 String 변환
    private String userCi;

    // 주소
    private Integer userZip;
    private String userJibunAddr;
    private String userRoadAddr;
    private String userDetailAddr;

    // 부가 정보
    private Long userBcode;     // 공동현관번호 등 숫자형이면 Long/Integer
    private Integer userSkin;   // 1:지성/2:건성 등 주석에 정의
    private String userColor;   // 선호 색상 등등 ~ 
    private Integer userLevel;  // 등급
    private Integer userStatus; // 1:정상/2:휴면 등등 ~ 

    // 생년월일은 DATE → 필요 시 LocalDate
    private LocalDate userBirth;

    // 시스템 공통
    private LocalDateTime userUpdate;
    private LocalDateTime userRegdate;
}
