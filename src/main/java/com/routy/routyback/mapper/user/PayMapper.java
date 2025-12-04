package com.routy.routyback.mapper.user;

import com.routy.routyback.dto.OrdersDTO;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface PayMapper {

    void insertOrder(OrdersDTO ordersDto);

    void insertPayProduct(Map<String, Object> itemMap);
    
    OrdersDTO selectOrder(@Param("odNo") Long odNo);

    void insertPaySuccess(Map<String, Object> payMap);
}