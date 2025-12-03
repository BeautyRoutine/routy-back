/**
 * 주문 서비스 인터페이스
 * 주문 요약 / 목록 / 상세 조회 기능을 제공합니다.
 * 비즈니스 로직 분리를 위해 사용합니다.
 * @author 김지용
 */
package com.routy.routyback.service.user;

import com.routy.routyback.dto.order.*;

import java.util.List;

public interface IOrderService {

    /**
     * 주문 상태 요약 조회
     */
    OrderStatusSummaryResponse getSummary(Long userNo);

    /**
     * 주문 목록 조회
     */
    List<OrderListItemResponse> getList(Long userNo);

    /**
     * 주문 상세 조회
     */
    OrderDetailResponse getDetail(Long odNo);
}