package com.routy.routyback.service.admin;

import java.util.Map;

import com.routy.routyback.common.ApiResponse;
import com.routy.routyback.dto.DeliveryDTO;

public interface IOrderAdmService {
	ApiResponse listAllOrders(Map<String, Object> params); // 전체 주문 조회
	ApiResponse detailOrder(int odNo); // 주문번호 조회
	ApiResponse detailPrdOrder(int odNo); // 주문번호 제품목록 조회
	ApiResponse detailDelvOrder(int odNo); // 주문번호 택배목록 조회
	
	
	
	ApiResponse listAllOrdersDelivery(Map<String, Object> params); // 전체 택배 조회
	ApiResponse detailOrderDelivery(int delvNo); // 택배번호 조회
	ApiResponse insertOrderDelivery(DeliveryDTO dto); // 택배 접수
}
