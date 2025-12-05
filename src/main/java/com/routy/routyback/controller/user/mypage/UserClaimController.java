package com.routy.routyback.controller.user.mypage;

import com.routy.routyback.common.ApiResponse;
import com.routy.routyback.dto.user.mypage.UserClaimRequest;
import com.routy.routyback.dto.user.mypage.UserClaimResponse;
import com.routy.routyback.service.user.mypage.IUserClaimService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 마이페이지 - 취소/반품/교환 클레임 컨트롤러
 * - 실제 API 엔드포인트 제공
 */
@RestController
@RequestMapping("/api/users/{userId}/claims")
@RequiredArgsConstructor
public class UserClaimController {

    private final IUserClaimService userClaimService;

    /**
     * 클레임 신청
     */
    @PostMapping
    public ApiResponse<Void> createClaim(
        @PathVariable String userId,
        @RequestBody UserClaimRequest request) {

        userClaimService.createClaim(userId, request);
        return ApiResponse.success(null);
    }

    /**
     * 클레임 목록 조회
     */
    @GetMapping
    public ApiResponse<List<UserClaimResponse>> getClaims(
        @PathVariable String userId) {

        return ApiResponse.success(userClaimService.getClaims(userId));
    }
}