package com.routy.routyback.dto;

import lombok.Data;

@Data
public class PaymentConfirmRequestDTO {

    private String paymentKey; // 토스 결제 키
    private String orderId;    // 주문번호
    private Long amount;       // 결제 금액
}