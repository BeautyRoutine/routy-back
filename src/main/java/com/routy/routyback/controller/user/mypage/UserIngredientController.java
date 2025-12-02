package com.routy.routyback.controller.user.mypage;

import com.routy.routyback.common.ApiResponse;
import com.routy.routyback.service.user.mypage.IUserIngredientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 사용자 선호/기피 성분 관리 컨트롤러
 * - 전체 조회
 * - 성분 추가
 * - 성분 삭제
 */
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserIngredientController {

    // 선호/기피 성분 관련 서비스
    private final IUserIngredientService userIngredientService;

    /**
     * 사용자 선호/기피 성분 전체 조회 API
     * @param userId 사용자 번호
     * @return focus/avoid 성분 목록
     */
    @GetMapping("/{userId}/ingredients")
    public ResponseEntity<ApiResponse<?>> getUserIngredients(@PathVariable Long userId) {
        return ResponseEntity.ok(ApiResponse.success(
            userIngredientService.getUserIngredients(userId)
        ));
    }

    /**
     * 선호/기피 성분 추가 API
     * @param userId 사용자 번호
     * @param body ingredientId, type(FOCUS/AVOID)
     */
    @PostMapping("/{userId}/ingredients")
    public ResponseEntity<ApiResponse<?>> addIngredient(
        @PathVariable Long userId,
        @RequestBody Map<String, Object> body
    ) {
        Long ingredientId = Long.valueOf(body.get("ingredientId").toString());
        String type = body.get("type").toString();

        userIngredientService.addIngredient(userId, ingredientId, type);
        return ResponseEntity.ok(ApiResponse.success(null));
    }

    /**
     * 선호/기피 성분 삭제 API
     * @param userId 사용자 번호
     * @param ingredientId 성분 번호
     * @param type FOCUS/AVOID
     */
    @DeleteMapping("/{userId}/ingredients/{ingredientId}")
    public ResponseEntity<ApiResponse<?>> removeIngredient(
        @PathVariable Long userId,
        @PathVariable Long ingredientId,
        @RequestParam String type
    ) {
        userIngredientService.removeIngredient(userId, ingredientId, type);
        return ResponseEntity.ok(ApiResponse.success(null));
    }
}