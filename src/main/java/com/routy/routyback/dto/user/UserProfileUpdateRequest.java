/**
 * 사용자 프로필 수정 요청 DTO
 * 수정 가능한 회원 데이터를 전달받습니다.
 * @author 김지용
 */

package com.routy.routyback.dto.user;

import lombok.Data;

@Data
public class UserProfileUpdateRequest {
    
    private String userName;          // 회원이름
    private String nickName;          // 닉네임
    private String email;             // 이메일
    private String phone;             // 연락처
    private String address;           // 주소
    private String zipCode;           // 우편번호
    private String skinType;          // 피부 타입 (단일 선택)
    private String profileImageUrl;   // 프로필 이미지 URL
}
