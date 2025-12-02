package com.routy.routyback.controller.user.mypage;

import com.routy.routyback.common.ApiResponse;
import com.routy.routyback.dto.user.mypage.UserReviewCreateRequest;
import com.routy.routyback.dto.user.mypage.UserReviewDetailResponse;
import com.routy.routyback.dto.user.mypage.UserReviewResponse;
import com.routy.routyback.dto.user.mypage.UserReviewUpdateRequest;
import com.routy.routyback.service.user.mypage.IUserReviewService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 마이페이지 - 나의 리뷰 API 컨트롤러
 * - 목록 조회
 * - 상세 조회
 * - 수정
 * - 삭제
 * ApiResponse 공통 응답 포맷 사용
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users/{userId}/reviews")
public class UserReviewController {

    // 나의 리뷰 비즈니스 로직 처리 서비스
    private final IUserReviewService userReviewService;

    /**
     * 나의 리뷰 목록 조회
     * GET /api/users/{userId}/reviews
     */
    @GetMapping
    public ApiResponse<List<UserReviewResponse>> getMyReviews(
        @PathVariable("userId") Long userId
    ) {
        List<UserReviewResponse> reviews = userReviewService.getUserReviews(userId);
        return ApiResponse.success(reviews);
    }

    /**
     * 나의 리뷰 상세 조회
     * GET /api/users/{userId}/reviews/{reviewId}
     */
    @GetMapping("/{reviewId}")
    public ApiResponse<?> getMyReviewDetail(
        @PathVariable("userId") Long userId,
        @PathVariable("reviewId") Long reviewId
    ) {
        UserReviewDetailResponse detail =
            userReviewService.getReviewDetail(userId, reviewId);

        if (detail == null) {
            return ApiResponse.error(404, "REVIEW_NOT_FOUND");
        }

        return ApiResponse.success(detail);
    }

    /**
     * 마이페이지 - 리뷰 관리 컨트롤러
     * 등록(create) 기능만 추가로 구현한다.
     */
    @PostMapping
    public ApiResponse<Void> createReview(
        @PathVariable Long userId,
        @RequestBody UserReviewCreateRequest request
    ) {
        // 서비스 호출해 리뷰 등록 처리
        userReviewService.createReview(userId, request);

        // 공통 응답 포맷
        return ApiResponse.success(null);
    }

    /**
     * 나의 리뷰 수정
     * PUT /api/users/{userId}/reviews/{reviewId}
     */
    @PutMapping("/{reviewId}")
    public ApiResponse<?> updateMyReview(
        @PathVariable("userId") Long userId,
        @PathVariable("reviewId") Long reviewId,
        @RequestBody UserReviewUpdateRequest request
    ) {
        boolean success = userReviewService.updateReview(userId, reviewId, request);

        if (!success) {
            return ApiResponse.error(404, "REVIEW_NOT_FOUND");
        }

        return ApiResponse.success(null);
    }

    /**
     * 나의 리뷰 삭제
     * DELETE /api/users/{userId}/reviews/{reviewId}
     */
    @DeleteMapping("/{reviewId}")
    public ApiResponse<?> deleteMyReview(
        @PathVariable("userId") Long userId,
        @PathVariable("reviewId") Long reviewId
    ) {
        boolean success = userReviewService.deleteReview(userId, reviewId);

        if (!success) {
            return ApiResponse.error(404, "REVIEW_NOT_FOUND");
        }

        return ApiResponse.success(null);
    }
}