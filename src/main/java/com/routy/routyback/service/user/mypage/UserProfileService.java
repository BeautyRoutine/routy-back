package com.routy.routyback.service.user.mypage;

import com.routy.routyback.dto.user.mypage.UserProfileResponse;
import com.routy.routyback.dto.user.mypage.UserProfileUpdateRequest;
import com.routy.routyback.mapper.user.mypage.UserProfileMapper;
import com.routy.routyback.domain.user.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 마이페이지 - 사용자 프로필 조회 서비스 구현체
 * author 김지용
 */
@Service
@RequiredArgsConstructor
public class UserProfileService implements IUserProfileService {

    private final UserProfileMapper userProfileMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserProfileResponse getUserProfile(String userId) {
        return userProfileMapper.selectUserProfile(userId);
    }

    @Override
    public boolean checkNickname(String nickname) {
        return userProfileMapper.countNickname(nickname) == 0; // 0이면 사용 가능
    }


    @Override
    public boolean deleteUser(String userId) {
        return userProfileMapper.softDeleteUser(userId) > 0;
    }

    @Override
    public boolean deleteUserWithPassword(String userId, String password) {

        User user = userProfileMapper.findByUserId(userId);
        if (user == null) {
            return false;
        }

        // 비밀번호 검증
        if (!passwordEncoder.matches(password, user.getUserPw())) {
            return false;
        }

        // Soft delete
        return userProfileMapper.softDeleteUser(userId) > 0;
    }

    @Override
    public boolean changePassword(String userId, String currentPassword, String newPassword) {

        User user = userProfileMapper.findByUserId(userId);

        if (user == null) {
            return false;
        }

        if (!passwordEncoder.matches(currentPassword, user.getUserPw())) {
            return false;
        }

        String encoded = passwordEncoder.encode(newPassword);
        userProfileMapper.updatePassword(userId, encoded);

        return true;
    }


    /**
     * 사용자 프로필 수정 서비스
     * 회원의 프로필 정보를 업데이트합니다.
     * @param userId 회원 아이디
     * @param req 수정 요청 DTO
     * @return boolean 업데이트 성공 여부
     */
    public boolean updateUserProfile(String userId, UserProfileUpdateRequest req) {
        int result = userProfileMapper.updateUserProfile(userId, req);
        return result > 0;
    }


}
