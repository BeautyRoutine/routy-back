package com.routy.routyback.service.user;

import com.routy.routyback.dto.user.UserProfileResponse;

/**
 * 마이페이지 - 사용자 프로필 조회 인터페이스
 */
public interface IUserProfileService {

    /**
     * 회원 번호로 프로필 조회
     */
    UserProfileResponse getUserProfile(Long userNo);
}