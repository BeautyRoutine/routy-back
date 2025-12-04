package com.routy.routyback.dto;

import java.util.List;
import lombok.Data;

@Data
public class OrderSaveRequestDTO {

    private int userNo;

    // 1. 배송지 정보
    private String receiverName;   // 수령인 (odName)
    private String receiverPhone;  // 연락처 (odHp)
    private Integer zipCode;       // 우편번호 (odZip)
    private String roadAddress;    // 도로명 주소 (odRoadAddr)
    private String detailAddress;  // 상세 주소 (odDetailAddr)
    private String deliveryMsg;    // 배송 메시지 (odDelvMsg)

    // 2. 결제 및 주문 정보
    private int totalAmount;       // 총 결제 금액
    private int deliveryFee;       // 배송비
    private String orderName;      // 주문명

    // 3. 상품 목록
    private List<OrderItemDto> items;

    @Data
    public static class OrderItemDto {

        private int prdNo;    // 상품번호
        private int quantity; // 수량
        private int price;    // 단가
    }
}