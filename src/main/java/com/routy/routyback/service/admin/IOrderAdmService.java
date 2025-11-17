package com.routy.routyback.service.admin;

import java.util.Map;

import com.routy.routyback.common.ApiResponse;

public interface IOrderAdmService {
	ApiResponse listAllOrders(Map<String, Object> params); // 전체 주문 조회
	ApiResponse detailOrder(int odNo);
}
