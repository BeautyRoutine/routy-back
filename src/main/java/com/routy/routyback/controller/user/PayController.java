package com.routy.routyback.controller.user;

import com.routy.routyback.dto.OrderSaveRequestDTO;
import com.routy.routyback.dto.PaymentConfirmRequestDTO;
import com.routy.routyback.service.user.PayService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class PayController {

    private final PayService payService;

    // 1. 주문 생성 API
    @PostMapping("/order")
    public ResponseEntity<Long> createOrder(@RequestBody OrderSaveRequestDTO request) {
        // 주문 저장 후, 생성된 '주문번호(odNo)'를 리턴
        Long odNo = payService.createOrder(request);
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
}