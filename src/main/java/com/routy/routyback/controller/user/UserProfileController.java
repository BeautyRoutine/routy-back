package com.routy.routyback.controller.user;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import com.routy.routyback.service.user.IUserProfileService;
import com.routy.routyback.dto.user.UserProfileResponse;

/**
 * 마이페이지 - 사용자 프로필 조회 컨트롤러
 * @author 김지용
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UserProfileController {

    private final IUserProfileService userProfileService;

    /**
     * 사용자 프로필 조회 API
     * GET /api/users/{userNo}/profile
     *
     * @param userNo 조회할 회원 번호
     * @return 사용자 프로필 요약 정보
     * @author 김지용
     */
    @GetMapping("/{userNo}/profile")
    public UserProfileResponse getUserProfile(@PathVariable Long userNo) {
        return userProfileService.getUserProfile(userNo);
    }
}
