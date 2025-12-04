package com.routy.routyback.service.admin;

import java.sql.Timestamp;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.routy.routyback.mapper.admin.IOrdersAdmDAO;
import com.routy.routyback.mapper.admin.IOrderDetailAdmDAO;
import com.routy.routyback.common.ApiResponse;
import com.routy.routyback.common.ParamProcessor;
import com.routy.routyback.common.category.CategoryRepository;
import com.routy.routyback.dto.DeliveryDTO;
import com.routy.routyback.dto.OrderPrdDTO;
import com.routy.routyback.dto.OrdersUsDTO;

@Service
public class OrderAdmService implements IOrderAdmService {
	@Autowired
	@Qualifier("IOrdersAdmDAO")
	IOrdersAdmDAO dao;
	
	@Autowired
	@Qualifier("IOrderDetailAdmDAO")
	IOrderDetailAdmDAO detdao;
	
	@Autowired
	CategoryRepository cateRepo;



	@Override
	public ApiResponse listAllOrders(Map<String, Object> params) {
		try {
			// param 재가공
			ParamProcessor.paging(params);
			ParamProcessor.likeBothString(params, "mem_name");
			
			int total = dao.listAllOrdersCount(params);

			ArrayList<OrdersUsDTO> resultList = dao.listAllOrders(params);
	        
	        Map<String, Object> result = new java.util.HashMap<>();
	        result.put("total", total);
	        result.put("list", resultList);
	        
	        return ApiResponse.success(result);
		} catch (Exception e) {
			return ApiResponse.fromException(e);
		}
	}

	@Override
	public ApiResponse detailOrder(int odNo) {
		try {
			OrdersUsDTO resultRow = dao.detailOrder(odNo);
			
			return ApiResponse.success(resultRow);
		} catch (Exception e) {
			return ApiResponse.fromException(e);
		}
	}
	
	@Override
	public ApiResponse detailPrdOrder(int odNo) {
		try {
			ArrayList<OrderPrdDTO> resultRow = detdao.detailPrdOrder(odNo);
			
			for(OrderPrdDTO row : resultRow) {
				int mainNo = row.getPrdMainCate();
				int subNo = row.getPrdSubCate();
				row.setMainCateStr(cateRepo.getMainCateStr(mainNo));
				row.setSubCateStr(cateRepo.getSubCateStr(subNo));
			}
			
			return ApiResponse.success(resultRow);
		} catch (Exception e) {
			return ApiResponse.fromException(e);
		}
	}
	
	@Override
	public ApiResponse detailDelvOrder(int odNo) {
		try {
			ArrayList<DeliveryDTO> resultRow = detdao.detailDelvOrder(odNo);
			
			return ApiResponse.success(resultRow);
		} catch (Exception e) {
			return ApiResponse.fromException(e);
		}
	}



	@Override
	public ApiResponse listAllOrdersDelivery(Map<String, Object> params) {
		try {
			// param 재가공
			ParamProcessor.paging(params);
			ParamProcessor.likeBothString(params, "mem_name");
			
			int total = dao.listAllOrdersDeliveryCount(params);

			ArrayList<DeliveryDTO> resultList = dao.listAllOrdersDelivery(params);
	        
	        Map<String, Object> result = new java.util.HashMap<>();
	        result.put("total", total);
	        result.put("list", resultList);
	        
	        return ApiResponse.success(result);
		} catch (Exception e) {
			return ApiResponse.fromException(e);
		}
	}

	@Override
	public ApiResponse detailOrderDelivery(int delvNo) {
		try {
			DeliveryDTO resultRow = dao.detailOrderDelivery(delvNo);
			
			return ApiResponse.success(resultRow);
		} catch (Exception e) {
			return ApiResponse.fromException(e);
		}
	}

	@Override
	public ApiResponse insertOrderDelivery(DeliveryDTO dto) {
		try {
			dao.insertOrderDelivery(dto);
			int delvNo = dto.getDelvNo();
			
			Map<String, Object> result = new java.util.HashMap<>();
	        result.put("delvNo", delvNo);
			
			return ApiResponse.success(result);
		} catch (Exception e) {
			return ApiResponse.fromException(e);
		}
		
	}

	@Override
	public ApiResponse updateOrderDelivery(DeliveryDTO dto) {
		try {
			dao.updateOrderDelivery(dto);
			int delvNo = dto.getDelvNo();
			
			Map<String, Object> result = new java.util.HashMap<>();
	        result.put("msg", "delvNo:"+delvNo+" 택배를 수정하였습니다.");
			
			return ApiResponse.success(result);
		} catch (Exception e) {
			return ApiResponse.fromException(e);
		}
	}

	@Override
	public ApiResponse deleteOrderDelivery(int delvNo) {
		try {
			dao.deleteOrderDelivery(delvNo);
			
			Map<String, Object> result = new java.util.HashMap<>();
	        result.put("msg", "delvNo:"+delvNo+" 택배를 삭제하였습니다.");
			
			return ApiResponse.success(result);
		} catch (Exception e) {
			return ApiResponse.fromException(e);
		}
	}

}
