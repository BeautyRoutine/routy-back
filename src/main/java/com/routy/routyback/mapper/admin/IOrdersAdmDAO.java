package com.routy.routyback.mapper.admin;

import java.util.List;
import java.util.Map;

import com.routy.routyback.dto.DeliveryDTO;

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
    
    
    
    
    
    /**
     * 검색 조건을 반영했을 때 조회되는 택배의 갯수 조회.
	 * @param params
	 * 	검색조건(mem_name:결제고객 성명, delv_s_start_day: 택배 접수일 조회 범위(이상), delv_s_end_day: 택배 접수일 조회 범위(이하), delv_e_start_day: 택배 완료일 조회 범위(이상), delv_e_end_day: 택배 완료일 조회 범위(이하))
	 * @return 조회된 레코드 갯수 (Int)
     */
    int listAllOrdersDeliveryCount(Map<String, Object> params);
    /**
     * 검색 조건을 반영했을 때 조회되는 택배 데이터들을 조회.
     * @param params
     * 	검색조건(mem_name:결제고객 성명, delv_s_start_day: 택배 접수일 조회 범위(이상), delv_s_end_day: 택배 접수일 조회 범위(이하), delv_e_start_day: 택배 완료일 조회 범위(이상), delv_e_end_day: 택배 완료일 조회 범위(이하))
     * 	페이징 범위 포함(offset, limit)
     * @return 택배 데이터들 (List)
     */
    List<Map<String, Object>> listAllOrdersDelivery(Map<String, Object> params);
    /**
     * delvNo에 해당되는 택배정보를 조회.
     * @param delvNo
     * 	택배번호
     * @return 택배 데이터 (Map)
     */
    Map<String, Object> detailOrderDelivery(int delvNo);
    /**
     * 새로운 택배를 등록.
     * @param dto
     * 	택배 정보
     */
    void insertOrderDelivery(DeliveryDTO dto);
    /**
     * delvNo에 해당되는 택배 정보를 수정.
     * @param dto
     * 	택배 정보
     */
    void updateOrderDelivery(DeliveryDTO dto);
    /**
     * delvNo에 해당되는 택배 정보를 삭제.
     * @param delvNo
     * 	택배 번호 (int)
     */
    void deleteOrderDelivery(int delvNo);
}
