/**
 * 주문 컨트롤러
 * 주문 상태 요약, 주문 목록, 주문 상세 조회 API를 제공합니다.
 * /api/orders 경로를 사용하여 단일 Order 도메인으로 관리합니다.
 * 공통 응답 구조(ApiResponse)를 적용합니다.
 * @author 김지용
 */
package com.routy.routyback.controller.user;

import com.routy.routyback.common.ApiResponse;
import com.routy.routyback.service.user.IOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrderController {

    private final IOrderService orderService;

    /**
     * 주문 상태 요약 조회
     */
    @GetMapping("/{userNo}/status-summary")
    public ResponseEntity<ApiResponse> summary(@PathVariable Long userNo) {
        return ResponseEntity.ok(ApiResponse.success(orderService.getSummary(userNo)));
    }

    /**
     * 주문 목록 조회
     */
    @GetMapping("/{userNo}")
    public ResponseEntity<ApiResponse> list(@PathVariable Long userNo) {
        return ResponseEntity.ok(ApiResponse.success(orderService.getList(userNo)));
    }

    /**
     * 주문 상세 조회
     */
    @GetMapping("/detail/{odNo}")
    public ResponseEntity<ApiResponse> detail(@PathVariable Long odNo) {
        return ResponseEntity.ok(ApiResponse.success(orderService.getDetail(odNo)));
    }
}