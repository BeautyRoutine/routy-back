package com.routy.routyback.service.admin;

import java.sql.Timestamp;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.routy.routyback.mapper.admin.IOrdersAdmDAO;
import com.routy.routyback.common.ApiResponse;
import com.routy.routyback.common.ParamProcessor;

@Service
public class OrderAdmService implements IOrderAdmService {
	@Autowired
	@Qualifier("IOrdersAdmDAO")
	IOrdersAdmDAO dao;
	
	// KST 포맷터
    private static final DateTimeFormatter KST_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
    		.withZone(ZoneId.of("Asia/Seoul"));
    // 날짜 필드 목록 (필요 시 추가 가능)
    private static final List<String> DATE_FIELDS = Arrays.asList(
            "ODREGDATE"
    		, "USERREGDATE", "USERUPDATE"
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

}
