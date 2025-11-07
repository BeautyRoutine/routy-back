package com.routy.routyback.service.admin;

import java.util.List;
import java.util.Map;

public interface IOrderAdmService {
	List<Map<String, Object>> listAllOrders(Map<String, Object> params); // 전체 주문 조회
}
