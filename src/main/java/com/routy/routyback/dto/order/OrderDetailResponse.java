/**
 * 주문 상세 응답 DTO
 * 주문 기본 정보 + 주문에 포함된 상품 목록을 제공합니다.
 * 내부 클래스 OrderItem은 주문 상품 상세 항목을 나타냅니다.
 * @author 김지용
 */
package com.routy.routyback.dto.order;

import lombok.Data;
import java.util.List;

@Data
public class OrderDetailResponse {

    private Long orderNo;
    private String orderDate;
    private Integer orderStatus;
    private Integer totalPrice;
    private Integer deliveryPrice;

    private List<OrderItem> items; // 주문 상품 목록

    /**
     * 주문 상세 내 개별 상품 정보 DTO
     */
    @Data
    public static class OrderItem {

        private Long productNo;
        private String productName;
        private String productImage;
        private Integer quantity;
        private Integer price;
        private Integer total;
    }
}