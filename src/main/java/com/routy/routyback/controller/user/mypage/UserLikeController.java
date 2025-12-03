package com.routy.routyback.controller.user.mypage;

import com.routy.routyback.common.ApiResponse;
import com.routy.routyback.service.user.mypage.IUserLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 사용자 좋아요 관련 REST API 컨트롤러입니다.
 * 좋아요 조회, 추가, 삭제, 체크 기능을 제공합니다.
 */
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserLikeController {

    private final IUserLikeService userLikeService;

    /**
     * 특정 사용자의 좋아요 목록 조회
     */
    @GetMapping("/{userId}/likes")
    public ResponseEntity<ApiResponse<?>> getUserLikes(
        @PathVariable String userId,
        @RequestParam(defaultValue = "PRODUCT") String type
    ) {
        return ResponseEntity.ok(ApiResponse.success(
            userLikeService.getUserLikeProducts(userId, type)
        ));
    }

    /**
     * 좋아요 추가
     */
    @PostMapping("/{userId}/likes/{productId}")
    public ResponseEntity<ApiResponse<?>> addLike(
        @PathVariable String userId,
        @PathVariable Long productId,
        @RequestParam(defaultValue = "PRODUCT") String type
    ) {
        userLikeService.addLike(userId, productId, type);
        return ResponseEntity.ok(ApiResponse.success(null));
    }

    /**
     * 좋아요 삭제
     */
    @DeleteMapping("/{userId}/likes/{productId}")
    public ResponseEntity<ApiResponse<?>> removeLike(
        @PathVariable String userId,
        @PathVariable Long productId,
        @RequestParam(defaultValue = "PRODUCT") String type
    ) {
        userLikeService.removeLike(userId, productId, type);
        return ResponseEntity.ok(ApiResponse.success(null));
    }

    /**
     * 특정 상품이 좋아요 되어있는지 판단
     */
    @GetMapping("/{userId}/likes/{productId}/exists")
    public ResponseEntity<ApiResponse<?>> isLiked(
        @PathVariable String userId,
        @PathVariable Long productId,
        @RequestParam(defaultValue = "PRODUCT") String type
    ) {
        boolean liked = userLikeService.isLiked(userId, productId, type);
        return ResponseEntity.ok(ApiResponse.success(liked));
    }
}