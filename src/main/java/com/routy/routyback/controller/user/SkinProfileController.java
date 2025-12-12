package com.routy.routyback.controller.user;

import com.routy.routyback.common.ApiResponse;
import com.routy.routyback.dto.user.SkinProfileRequest;
import com.routy.routyback.service.user.SkinProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user/skin-profile")
@RequiredArgsConstructor
public class SkinProfileController {

    private final SkinProfileService skinProfileService;

    @PostMapping
    public ApiResponse<?> saveSkinProfile(
            @RequestHeader("Authorization") String token,
            @RequestBody SkinProfileRequest request) {
        try {
            skinProfileService.saveSkinProfile(token, request);
            return ApiResponse.success("피부 타입이 저장되었습니다.");
        } catch (Exception e) {
            return ApiResponse.error(500, "피부 타입 저장 실패: " + e.getMessage());
        }
    }

    @GetMapping
    public ApiResponse<?> getSkinProfile(
            @RequestHeader("Authorization") String token) {
        try {
            Integer skinType = skinProfileService.getSkinProfile(token);
            if (skinType == null) {
                return ApiResponse.error(404, "피부 타입 정보가 없습니다.");
            }
            return ApiResponse.success(skinType);
        } catch (Exception e) {
            return ApiResponse.error(500, "피부 타입 조회 실패: " + e.getMessage());
        }
    }

    @GetMapping("/completed")
    public ApiResponse<?> isSkinProfileCompleted(
            @RequestHeader("Authorization") String token) {
        try {
            boolean isCompleted = skinProfileService.isSkinProfileCompleted(token);
            return ApiResponse.success(isCompleted);
        } catch (Exception e) {
            return ApiResponse.error(500, "확인 실패: " + e.getMessage());
        }
    }
}