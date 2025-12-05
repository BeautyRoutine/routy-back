package com.routy.routyback.controller.user;

import com.routy.routyback.common.ApiResponse;
import com.routy.routyback.dto.notification.NotificationResponse;
import com.routy.routyback.service.notification.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 헤더 알림 벨 아이콘용 사용자 알림 API
 * Base URL: /api/users/{userId}/notifications
 */
@RestController
@RequestMapping("/api/users/{userId}/notifications")
@RequiredArgsConstructor
public class NotificationController {

    // 알림 비즈니스 로직
    private final NotificationService notificationService;

    /**
     * [GET] /api/users/{userId}/notifications/count
     * - 읽지 않은 알림 개수 조회
     * - Response: { "count": 3 }
     */
    @GetMapping("/count")
    public ApiResponse<Map<String, Integer>> getUnreadCount(@PathVariable String userId) {
        int count = notificationService.getUnreadCount(userId);

        Map<String, Integer> body = new HashMap<>();
        body.put("count", count);

        return ApiResponse.success(body);
    }

    /**
     * [GET] /api/users/{userId}/notifications
     * - 알림 목록 조회
     * - 최신순으로 정렬된 리스트 반환
     */
    @GetMapping
    public ApiResponse<List<NotificationResponse>> getNotifications(@PathVariable String userId) {
        List<NotificationResponse> notifications = notificationService.getNotifications(userId);
        return ApiResponse.success(notifications);
    }

    /**
     * [POST] /api/users/{userId}/notifications/read
     * - 사용자의 모든 알림을 읽음 처리
     * - 별도 바디 없이 200만 내려줌
     */
    @PostMapping("/read")
    public ApiResponse<Void> readAll(@PathVariable String userId) {
        notificationService.readAll(userId);
        return ApiResponse.success(null);
    }

    /**
     * [POST] /api/users/{userId}/notifications/{notiId}/read
     * - 특정 알림 하나만 읽음 처리
     */
    @PostMapping("/{notiId}/read")
    public ApiResponse<Void> readOne(
        @PathVariable String userId,
        @PathVariable Long notiId
    ) {
        notificationService.readOne(userId, notiId);
        return ApiResponse.success(null);
    }

    /**
     * [DELETE] /api/users/{userId}/notifications/{notiId}
     * - 특정 알림 하나 삭제
     */
    @DeleteMapping("/{notiId}")
    public ApiResponse<Void> deleteOne(
        @PathVariable String userId,
        @PathVariable Long notiId
    ) {
        notificationService.deleteOne(userId, notiId);
        return ApiResponse.success(null);
    }

    /**
     * [DELETE] /api/users/{userId}/notifications
     * - 사용자의 모든 알림 삭제
     */
    @DeleteMapping
    public ApiResponse<Void> deleteAll(@PathVariable String userId) {
        notificationService.deleteAll(userId);
        return ApiResponse.success(null);
    }

}