/**
 * 주문/배송 현황 요약 Mapper
 * 회원의 주문 상태별 개수를 조회합니다.
 * @author 김지용
 */
package com.routy.routyback.mapper.user;

import com.routy.routyback.dto.order.OrderStatusSummaryResponse;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface OrderStatusSummaryMapper {

    OrderStatusSummaryResponse getOrderStatusSummary(@Param("userNo") Long userNo);
}