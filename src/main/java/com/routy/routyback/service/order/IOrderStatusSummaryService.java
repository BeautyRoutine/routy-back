/**
 * 주문/배송 상태 요약 조회 인터페이스
 * 회원의 주문 상태 개수를 조회하는 기능을 제공합니다.
 * @author 김지용
 */
package com.routy.routyback.service.order;

import com.routy.routyback.dto.order.OrderStatusSummaryResponse;

public interface IOrderStatusSummaryService {

    /**
     * 주문 상태 요약 정보 조회
     * @param userNo 회원 번호
     * @return OrderStatusSummaryResponse 상태별 개수
     */
    OrderStatusSummaryResponse getOrderStatusSummary(Long userNo);
}