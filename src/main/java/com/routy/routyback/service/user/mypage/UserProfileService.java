package com.routy.routyback.service.user.mypage;

import com.routy.routyback.dto.user.mypage.UserProfileResponse;
import com.routy.routyback.dto.user.mypage.UserProfileUpdateRequest;
import com.routy.routyback.mapper.user.mypage.UserProfileMapper;
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

    /**
     * 사용자 프로필 수정 서비스
     * 회원의 프로필 정보를 업데이트합니다.
     * @param userNo 회원 번호
     * @param req 수정 요청 DTO
     * @return boolean 업데이트 성공 여부
     */
    public boolean updateUserProfile(Long userNo, UserProfileUpdateRequest req) {
        int result = userProfileMapper.updateUserProfile(userNo, req);
        return result > 0;
    }
}
