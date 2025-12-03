package com.routy.routyback.mapper.admin;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;

import com.routy.routyback.dto.OrderDelvDTO;
import com.routy.routyback.dto.OrderPrdDTO;

@Mapper
public interface IOrderDetailAdmDAO {
	/**
	 * 주문 상세 페이지에서 보여줄 주문에 포함된 제품 목록을 받아옴
	 * @param odNo
	 * 	주문번호
	 * @return 주문 제품 목록 (ArrayList)
	 */
	ArrayList<OrderPrdDTO> detailPrdOrder(int odNo);
	/**
	 * 주문 상세 페이지에서 보여줄 주문에 포함된 택배 목록을 받아옴
	 * @param odNo
	 * 	주문번호
	 * @return 주문 택배 목록 (ArrayList)
	 */
	ArrayList<OrderDelvDTO> detailDelvOrder(int odNo);

}
