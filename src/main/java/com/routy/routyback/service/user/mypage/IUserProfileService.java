package com.routy.routyback.service.user.mypage;

import com.routy.routyback.dto.user.mypage.UserProfileResponse;
import com.routy.routyback.dto.user.mypage.UserProfileUpdateRequest;

public interface IUserProfileService {

    /**
     * 사용자 프로필 조회 서비스
     * 회원 번호로 프로필 정보를 조회합니다.
     * @param userNo 회원 번호
     * @return UserProfileResponse 조회된 프로필 정보
     */
    UserProfileResponse getUserProfile(Long userNo);

    /**
     * 사용자 프로필 수정 서비스
     * 회원의 프로필 정보를 업데이트합니다.
     * @param userNo 회원 번호
     * @param req 수정 요청 DTO
     * @return boolean 수정 성공 여부
     */
    boolean updateUserProfile(Long userNo, UserProfileUpdateRequest req);
}