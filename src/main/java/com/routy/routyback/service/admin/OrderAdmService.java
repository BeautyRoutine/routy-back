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
import com.routy.routyback.dto.OrderDelvDTO;
import com.routy.routyback.dto.OrderPrdDTO;

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
	
	// KST 포맷터
    private static final DateTimeFormatter KST_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
    		.withZone(ZoneId.of("Asia/Seoul"));
    // 날짜 필드 목록 (필요 시 추가 가능)
    private static final List<String> DATE_FIELDS = Arrays.asList(
            "ODREGDATE"
    		, "USERREGDATE", "USERUPDATE"
    		, "DELVREGDATE", "DELVENDDATE"
    );



	@Override
	public ApiResponse listAllOrders(Map<String, Object> params) {
		try {
			// param 재가공
			ParamProcessor.paging(params);
			ParamProcessor.likeBothString(params, "mem_name");
			
			int total = dao.listAllOrdersCount(params);

	        List<Map<String, Object>> resultList = dao.listAllOrders(params);
	        for (Map<String, Object> row : resultList) {
	            for (String field : DATE_FIELDS) {
	                Object value = row.get(field);
	                if (value instanceof Timestamp ts) {
	                    row.put(field, KST_FORMATTER.format(ts.toInstant()));
	                }
	            }
	        }
	        
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
			Map<String, Object> resultRow = dao.detailOrder(odNo);
			for (String field : DATE_FIELDS) {
	            Object value = resultRow.get(field);
	            if (value instanceof Timestamp ts) {
	            	resultRow.put(field, KST_FORMATTER.format(ts.toInstant()));
	            }
	        }
			
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
			ArrayList<OrderDelvDTO> resultRow = detdao.detailDelvOrder(odNo);
			
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

	        List<Map<String, Object>> resultList = dao.listAllOrdersDelivery(params);
	        for (Map<String, Object> row : resultList) {
	            for (String field : DATE_FIELDS) {
	                Object value = row.get(field);
	                if (value instanceof Timestamp ts) {
	                    row.put(field, KST_FORMATTER.format(ts.toInstant()));
	                }
	            }
	        }
	        
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
			Map<String, Object> resultRow = dao.detailOrderDelivery(delvNo);
			for (String field : DATE_FIELDS) {
	            Object value = resultRow.get(field);
	            if (value instanceof Timestamp ts) {
	            	resultRow.put(field, KST_FORMATTER.format(ts.toInstant()));
	            }
	        }
			
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
			dao.insertOrderDelivery(dto);
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
