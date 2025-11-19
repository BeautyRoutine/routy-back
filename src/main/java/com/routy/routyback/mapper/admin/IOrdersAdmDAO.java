package com.routy.routyback.mapper.admin;

import java.util.List;
import java.util.Map;

public interface IOrdersAdmDAO {
	/**
	 * 검색 조건을 반영했을 때 조회되는 주문의 갯수 조회.
	 * @param params
	 * 	검색조건(mem_name:결제고객 성명, od_start_day: 결제일 조회 범위(이상), od_end_day: 결제일 조회 범위(이하))
	 * @return 조회된 레코드 갯수 (Int)
	 */
    int listAllOrdersCount(Map<String, Object> params);
    /**
     * 검색 조건을 반영했을 때 조회되는 주문 데이터들을 조회.
     * @param params
     * 	검색조건(mem_name:결제고객 성명, od_start_day: 결제일 조회 범위(이상), od_end_day: 결제일 조회 범위(이하))
     * 	페이징 범위 포함(offset, limit)
     * @return 주문 데이터들 (List)
     */
    List<Map<String, Object>> listAllOrders(Map<String, Object> params);
    /**
     * odNo에 해당되는 주문정보를 조회.
     * @param odNo
     * 	주문번호
     * @return 주문 데이터 (Map)
     */
    Map<String, Object> detailOrder(int odNo);
}
