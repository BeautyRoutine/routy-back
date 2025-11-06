package com.routy.routyback.mapper.admin;

import java.util.List;
import java.util.Map;

public interface IOrdersAdmDAO {
	List<Map<String, Object>> listAllOrders(); // 전체 주문 조회

}
