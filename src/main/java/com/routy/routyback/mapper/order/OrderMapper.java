/**
 * 주문 관련 Mapper
 * 주문 상태 요약, 주문 목록, 주문 상세 등
 * @author 김지용
 */
package com.routy.routyback.mapper.order;

import com.routy.routyback.dto.order.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface OrderMapper {

    /**
     * 주문 상태 요약 조회
     */
    OrderStatusSummaryResponse getOrderStatusSummary(@Param("userNo") Long userNo);

    /**
     * 주문 목록 조회
     */
    List<OrderListItemResponse> getOrderList(@Param("userNo") Long userNo);

    /**
     * 주문 상세 조회
     */
    OrderDetailResponse getOrderDetail(@Param("odNo") Long odNo);

    /**
     * 주문 상세 내 상품 목록 조회
     */
    List<OrderDetailResponse.OrderItem> getOrderItems(@Param("odNo") Long odNo);
}