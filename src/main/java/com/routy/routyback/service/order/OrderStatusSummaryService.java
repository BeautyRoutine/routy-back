/**
 * 주문/배송 상태 요약 서비스 구현체
 * 주문 상태별 개수를 DB에서 조회합니다.
 * @author 김지용
 */
package com.routy.routyback.service.order;

import com.routy.routyback.dto.order.OrderStatusSummaryResponse;
import com.routy.routyback.mapper.user.OrderStatusSummaryMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderStatusSummaryService implements IOrderStatusSummaryService {

    private final OrderStatusSummaryMapper orderStatusSummaryMapper;

    @Override
    public OrderStatusSummaryResponse getOrderStatusSummary(Long userNo) {
        return orderStatusSummaryMapper.getOrderStatusSummary(userNo);
    }
}