package com.routy.routyback.controller.user;

import com.routy.routyback.dto.OrderSaveRequestDTO;
import com.routy.routyback.dto.PaymentConfirmRequestDTO;
import com.routy.routyback.mapper.user.UserMapper;
import com.routy.routyback.service.user.PayService;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PayController {

    private final PayService payService;
    private final UserMapper userMapper;

    private Long getAuthenticatedUserNo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || authentication.getName() == null || "anonymousUser".equals(
            authentication.getName())) {
            throw new RuntimeException("로그인 인증 정보가 없습니다.");
        }

        String currentUserId = authentication.getName();

        Long userNo = userMapper.findUserNoByUserId(currentUserId);

        if (userNo == null) {
            throw new RuntimeException("존재하지 않는 사용자입니다: " + currentUserId);
        }
        return userNo;
    }

    // 1. 주문 생성 API
    @PostMapping("/order")
    public ResponseEntity<Long> createOrder(@RequestBody OrderSaveRequestDTO request) {
        // 토큰에서 userNo를 안전하게 가져옴 (인증)
        Long authenticatedUserNo = getAuthenticatedUserNo();

        // 주문 저장 시, 검증된 userNo를 Service로 전달
        Long odNo = payService.createOrder(authenticatedUserNo, request);
        return ResponseEntity.ok(odNo);
    }

    // 2. 결제 승인 API
    @PostMapping("/confirm")
    public ResponseEntity<String> confirmPayment(@RequestBody PaymentConfirmRequestDTO request) {
        try {
            payService.confirmPayment(request);
            return ResponseEntity.ok("결제 승인 완료");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // 3. 결제 취소 API
    @PostMapping("/cancel")
    public ResponseEntity<String> cancelPayment(@RequestBody Map<String, Object> request) {
        try {
            // odNo와 cancelReason을 Map에서 추출
            Long odNo = Long.valueOf(String.valueOf(request.get("odNo")));
            String cancelReason = (String) request.get("cancelReason");

            if (cancelReason == null || cancelReason.isEmpty()) {
                cancelReason = "상품 단순 변심"; // 토스 취소 API의 필수 파라미터
            }

            payService.cancelPayment(odNo, cancelReason);
            return ResponseEntity.ok("결제 취소(환불) 완료");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}