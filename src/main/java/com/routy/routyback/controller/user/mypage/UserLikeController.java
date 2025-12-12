package com.routy.routyback.controller.user.mypage;

import com.routy.routyback.common.ApiResponse;
import com.routy.routyback.dto.user.mypage.UserLikeResponse;
import com.routy.routyback.service.user.mypage.IUserLikeService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 사용자 좋아요 관련 REST API 컨트롤러입니다. 좋아요 조회, 추가, 삭제, 체크 기능을 제공합니다.
 */
@RestController
@RequestMapping("/api/likes")
@RequiredArgsConstructor
public class UserLikeController {

    private final IUserLikeService userLikeService;

    /**
     * 특정 사용자의 좋아요 목록 조회
     */
    @GetMapping
    public ResponseEntity<ApiResponse<?>> getUserLikes(
        Authentication authentication,
        @RequestParam(defaultValue = "PRODUCT") String type
    ) {
        String userId = authentication.getName(); // JWT에서 추출
        return ResponseEntity.ok(ApiResponse.success(
            userLikeService.getUserLikeProducts(userId, type)
        ));
    }

    /**
     * 좋아요 추가
     */
    @PostMapping("/{productId}")
    public ResponseEntity<ApiResponse<?>> addLike(
        Authentication authentication,
        @PathVariable Long productId,
        @RequestParam(defaultValue = "PRODUCT") String type
    ) {
        String userId = authentication.getName();
        userLikeService.addLike(userId, productId, type);

        // 추가 후 전체 목록 반환 (Redux 상태 업데이트)
        List<UserLikeResponse> likes = userLikeService.getUserLikeProducts(userId, type);
        return ResponseEntity.ok(ApiResponse.success(likes));
    }

    /**
     * 좋아요 삭제
     */
    @DeleteMapping("/{productId}")
    public ResponseEntity<ApiResponse<?>> removeLike(
        Authentication authentication,
        @PathVariable Long productId,
        @RequestParam(defaultValue = "PRODUCT") String type
    ) {
        String userId = authentication.getName();
        userLikeService.removeLike(userId, productId, type);

        // 삭제 후 전체 목록 반환 (Redux 상태 업데이트)
        List<UserLikeResponse> likes = userLikeService.getUserLikeProducts(userId, type);
        return ResponseEntity.ok(ApiResponse.success(likes));
    }

    /**
     * 특정 상품이 좋아요 되어있는지 판단
     */
    @GetMapping("/{productId}/exists")
    public ResponseEntity<ApiResponse<?>> isLiked(
        Authentication authentication,
        @PathVariable Long productId,
        @RequestParam(defaultValue = "PRODUCT") String type
    ) {
        String userId = authentication.getName();
        boolean liked = userLikeService.isLiked(userId, productId, type);
        return ResponseEntity.ok(ApiResponse.success(liked));
    }
}