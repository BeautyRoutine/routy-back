/**
 * 주문 상태 요약 응답 DTO
 * 사용자 마이페이지 상단에 보여주는 주문/배송 현황 데이터입니다.
 * 주문 상태(1~7)별 카운트를 포함합니다.
 * @author 김지용
 */
package com.routy.routyback.dto.order;

import lombok.Data;

@Data
public class OrderStatusSummaryResponse {

    private Integer paymentComplete; // 1: 주문 완료
    private Integer preparing;       // 2: 준비중
    private Integer shipping;        // 3: 배송중
    private Integer delivered;       // 4: 배송 완료
    private Integer canceled;        // 5: 취소
    private Integer returned;        // 6: 반품
    private Integer exchanged;       // 7: 교환
}