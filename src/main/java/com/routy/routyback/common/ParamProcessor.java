package com.routy.routyback.common;

import java.util.Map;

public final class ParamProcessor {

	private ParamProcessor() { } // 인스턴스 방지

	public static void paging(Map<String, Object> params) {
        int page = parseInt(params.get("page"), 1); // int 변환 실패시 1 유지
        int pageGap = parseInt(params.get("page_gap"), 10); // int 변환 실패시 10유지

        int offset = (page - 1) * pageGap;
        params.put("offset", String.valueOf(offset));
        params.put("limit", String.valueOf(pageGap));
    }
	
	public static void likeBothString(Map<String, Object> params, String key) {
        if (params.containsKey(key)) {
            Object value = params.get(key);
            if (value != null) {
                params.put(key, "%" + value.toString() + "%");
            }
        }
    }



	private static int parseInt(Object value, int defaultValue) {
        try {
            return value != null ? Integer.parseInt(value.toString()) : defaultValue;
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

}
