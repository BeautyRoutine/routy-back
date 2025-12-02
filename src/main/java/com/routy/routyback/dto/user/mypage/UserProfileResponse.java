/**
 * 사용자 프로필 응답 DTO
 * @author 김지용
 */
package com.routy.routyback.dto.user.mypage;

import lombok.Data;

@Data
public class UserProfileResponse {

    private Long userNo;    // 회원번호
    private String userName;  // 회원이름
    private Integer userLevel; // 회원레벨
    private Integer reviewCount; // 리뷰개수
    private Integer points; // 포인트
    private String nickName; // 닉네임
    private String profileImageUrl; // 프로필 이미지 URL

    // 필요 시 : 쿠폰 개수, 피부 타입 등 확장 가능
    private String email;            // 이메일
    private String phone;            // 연락처
    private String address;          // 주소
    private String zipCode;          // 우편번호

    private Integer couponCount;     // 보유 쿠폰 수

    private String skinType;       // 피부 타입 리스트

}
