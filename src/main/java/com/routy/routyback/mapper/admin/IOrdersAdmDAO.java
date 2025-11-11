package com.routy.routyback.mapper.admin;

import java.util.List;
import java.util.Map;

public interface IOrdersAdmDAO {
    int listAllOrdersCount(Map<String, Object> params); // 전체 주문 조회 레코드수
    List<Map<String, Object>> listAllOrders(Map<String, Object> params); // 전체 주문 조회
}
