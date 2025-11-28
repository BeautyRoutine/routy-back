package com.routy.routyback.controller.user;

import com.routy.routyback.dto.order.OrderStatusSummaryResponse;
import com.routy.routyback.common.ApiResponse;
import com.routy.routyback.service.order.IOrderStatusSummaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 주문 관련 컨트롤러
 * 주문/배송 상태 요약, 주문 목록, 주문 상세 등
 * @author 김지용
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrderController {

    private final IOrderStatusSummaryService orderStatusSummaryService;

    /**
     * 주문/배송 상태 요약 조회 API
     * @param userNo 회원 번호
     * @return 주문 상태 요약 정보
     */
    @GetMapping("/{userNo}/status-summary")
    public ResponseEntity<ApiResponse> getOrderStatusSummary(@PathVariable Long userNo) {

        OrderStatusSummaryResponse data =
            orderStatusSummaryService.getOrderStatusSummary(userNo);

        return ResponseEntity.ok(ApiResponse.success(data));
    }

}