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
	public Map<String, Object> listAllOrders(Map<String, Object> params) {
		// param 재가공
		int page = 1;
		if(params.containsKey("page")) {
			try {
				page = Integer.parseInt((String)params.get("page"));
			} catch (Exception e) {
				// int로 변환 실패시 1 유지
			}
		}
		int pageGap = 10;
		if(params.containsKey("page_gap")) {
			try {
				pageGap = Integer.parseInt((String)params.get("page_gap"));
			} catch (Exception e) {
				// int로 변환 실패시 1 유지
			}
		}
		if(params.containsKey("mem_name")) {
			params.put("mem_name", "%"+params.get("mem_name")+"%");
		}
		
		// 페이징
		int offset = (page - 1) * pageGap;
		params.put("offset", String.valueOf(offset));
		params.put("limit", String.valueOf(pageGap));
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

		return result;
	}

}
