package com.routy.routyback.controller.user;

import com.routy.routyback.common.ApiResponse;
import com.routy.routyback.dto.user.SkinProfileRequest;
import com.routy.routyback.service.user.SkinProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class SkinProfileController {
    private final SkinProfileService skinProfileService;

    @PostMapping("/skin-profile")
    public ResponseEntity<?> saveSkinProfile(@RequestBody SkinProfileRequest request,
                                             @RequestHeader("Authorization") String token) {
        try {
            if (request.getSkinType() == null) {
                return ResponseEntity.badRequest()
                        .body(ApiResponse.error(400, "피부타입을 선택해주세요"));
            }
            
            skinProfileService.saveSkinProfile(token, request);
            return ResponseEntity.ok(ApiResponse.success("피부 프로필 저장 완료"));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500)
                    .body(ApiResponse.fromException(e));
        }
    }

    @GetMapping("/skin-profile")
    public ResponseEntity<?> getSkinProfile(@RequestHeader("Authorization") String token) {
        try {
            Map<String, Object> profile = skinProfileService.getSkinProfile(token);
            return ResponseEntity.ok(ApiResponse.success(profile));
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(ApiResponse.fromException(e));
        }
    }

    @GetMapping("/skin-profile/completed")
    public ResponseEntity<?> isProfileCompleted(@RequestHeader("Authorization") String token) {
        try {
            boolean completed = skinProfileService.isProfileCompleted(token);
            return ResponseEntity.ok(ApiResponse.success(completed));
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(ApiResponse.fromException(e));
        }
    }
}