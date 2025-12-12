package com.routy.routyback.service.user.mypage;

import com.routy.routyback.dto.user.mypage.UserProfileResponse;
import com.routy.routyback.dto.user.mypage.UserProfileUpdateRequest;

public interface IUserProfileService {

    /**
     * 사용자 프로필 조회 서비스
     * 회원 아이디로 프로필 정보를 조회합니다.
     * @param userId 회원 아이디
     * @return UserProfileResponse 조회된 프로필 정보
     */
    UserProfileResponse getUserProfile(String userId);

    /**
     * 사용자 프로필 수정 서비스
     * 회원의 프로필 정보를 업데이트합니다.
     * @param userId 회원 아이디
     * @param req 수정 요청 DTO
     * @return boolean 수정 성공 여부
     */
    boolean updateUserProfile(String userId, UserProfileUpdateRequest req);

    /**
     * 닉네임 중복 체크
     * 주어진 닉네임이 사용 가능한지 확인합니다.
     *
     * @param nickname 검사할 닉네임
     * @return true = 사용 가능, false = 이미 존재
     */
    boolean checkNickname(String nickname);

    boolean deleteUserWithPassword(String userId, String password);

    /**
     * 비밀번호 변경 서비스
     * @param userId
     * @param currentPassword
     * @param newPassword
     * @return
     */
    boolean changePassword(String userId, String currentPassword, String newPassword);

    /**
     * 회원 탈퇴(Soft Delete)
     * @param userId 회원 아이디
     * @return true = 성공, false = 실패
     */
    boolean deleteUser(String userId);
}