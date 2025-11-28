/**
 * 주문/배송 현황 요약 응답 DTO
 * 각 주문 상태별 개수를 제공합니다.
 * @author 김지용
 */
package com.routy.routyback.dto.order;

import lombok.Data;

@Data
public class OrderStatusSummaryResponse {

    private int paymentComplete; // 1: 주문완료
    private int preparing;       // 2: 준비중
    private int shipping;        // 3: 배송중
    private int delivered;       // 4: 배송완료
    private int canceled;        // 5: 취소
    private int returned;        // 6: 반품
    private int exchanged;       // 7: 교환
}