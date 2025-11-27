/**
 * 사용자 프로필 응답 DTO
 * @author 김지용
 */
package com.routy.routyback.dto.user;

import lombok.Data;

@Data
public class UserProfileResponse {

    private Long userNo;    // 회원번호
    private String userName;  // 회원이름
    private Integer userLevel; // 회원레벨
    private Integer reviewCount; // 리뷰개수
    private Integer points; // 포인트

    // 필요 시 : 쿠폰 개수, 피부 타입 등 확장 가능

}
