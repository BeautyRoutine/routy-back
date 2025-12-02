package com.routy.routyback.controller.user.mypage;

import com.routy.routyback.common.ApiResponse;
import com.routy.routyback.dto.user.mypage.UserProfileResponse;
import com.routy.routyback.dto.user.mypage.UserProfileUpdateRequest;
import com.routy.routyback.service.user.mypage.IUserProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

    /**
     * 사용자 프로필 수정 API
     * PUT /api/users/{userNo}/profile
     *
     * @param userNo 수정할 회원 번호
     * @param req 수정 요청 DTO
     * @return 공통 응답 포맷 (ApiResponse)
     * @author 김지용
     */
    @PutMapping("/{userNo}/profile")
    public ResponseEntity<ApiResponse> updateUserProfile(
        @PathVariable Long userNo,
        @RequestBody UserProfileUpdateRequest req
    ) {
        boolean updated = userProfileService.updateUserProfile(userNo, req);

        if (!updated) {
            return ResponseEntity
                .badRequest()
                .body(ApiResponse.error(400, "프로필 수정에 실패했습니다."));
        }

        return ResponseEntity.ok(ApiResponse.success("OK"));
    }


    // 닉네임 중복 체크 API
    @GetMapping("/check-nickname")
    public ApiResponse<Boolean> checkNickname(@RequestParam String nickname) {
        return ApiResponse.success(userProfileService.checkNickname(nickname));
    }

    /**
     * 회원 탈퇴 API
     * DELETE /api/users/{userNo}
     *
     * @param userNo 탈퇴할 회원 번호
     * @return 성공 여부 응답
     */
    @DeleteMapping("/{userNo}")
    public ApiResponse<Boolean> deleteUser(@PathVariable Long userNo) {
        boolean deleted = userProfileService.deleteUser(userNo);
        return ApiResponse.success(deleted);
    }
}
