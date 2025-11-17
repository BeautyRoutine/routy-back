package com.routy.routyback.service.admin;

import java.util.Map;

public interface IOrderAdmService {
	Map<String, Object> listAllOrders(Map<String, Object> params); // 전체 주문 조회
}
