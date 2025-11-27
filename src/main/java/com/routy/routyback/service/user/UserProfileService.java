package com.routy.routyback.service.user;

import com.routy.routyback.dto.user.UserProfileResponse;
import com.routy.routyback.mapper.user.UserProfileMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 마이페이지 - 사용자 프로필 조회 서비스 구현체
 * @author 김지용
 */
@Service
@RequiredArgsConstructor
public class UserProfileService implements IUserProfileService {

    private final UserProfileMapper userProfileMapper;

    @Override
    public UserProfileResponse getUserProfile(Long userNo) {
        return userProfileMapper.selectUserProfile(userNo);
    }
}