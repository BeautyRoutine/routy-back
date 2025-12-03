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

    /**
     * 닉네임 중복 체크
     * 주어진 닉네임이 사용 가능한지 확인합니다.
     *
     * @param nickname 검사할 닉네임
     * @return true = 사용 가능, false = 이미 존재
     */
    boolean checkNickname(String nickname);


    /**
     * 비밀번호 변경 서비스
     * @param userNo
     * @param currentPassword
     * @param newPassword
     * @return
     */
    boolean changePassword(Long userNo, String currentPassword, String newPassword);

    /**
     * 회원 탈퇴(Soft Delete)
     * @param userNo 회원 번호
     * @return true = 성공, false = 실패
     */
    boolean deleteUser(Long userNo);
}