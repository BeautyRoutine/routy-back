package com.routy.routyback.common;

import java.util.Map;

public final class ParamProcessor {

	private ParamProcessor() { } // 인스턴스 방지

	/**
	 * 페이징 기능을 적용하기 위해 offset과 limit를 자동으로 param에 추가하여 DAO로 넘길 수 있도록 해주는 함수.
	 * 페이지 기본값:1, 페이지갭(한 페이지 출력 레코드 수) 기본값:10.
	 * @param params
	 * 	클라이언트로부터 넘겨 받은 검색 조건이 담긴 Map 데이터.
	 */
	public static void paging(Map<String, Object> params) {
        int page = parseInt(params.get("page"), 1); // int 변환 실패시 1 유지
        int pageGap = parseInt(params.get("page_gap"), 10); // int 변환 실패시 10유지

        int offset = (page - 1) * pageGap;
        params.put("offset", String.valueOf(offset));
        params.put("limit", String.valueOf(pageGap));
    }
	
	/**
	 * LIKE절을 사용하기 위해 문자값 양옆에 %를 추가해주는 함수.
	 * @param params
	 * 	클라이언트로부터 넘겨 받은 검색 조건이 담긴 Map 데이터.
	 * @param key
	 * 	양옆에 %를 달아줄 문자값의 key 명칭.
	 */
	public static void likeBothString(Map<String, Object> params, String key) {
        if (params.containsKey(key)) {
            Object value = params.get(key);
            if (value != null) {
                params.put(key, "%" + value.toString() + "%");
            }
        }
    }



	/**
	 * 받은 value를 Int로 변환하는데 값이 없거나 변환에 실패하면 defaultValue로 대체하는 함수
	 * @param value
	 * 	Int로 변환할 값
	 * @param defaultValue
	 * 	대체할 기본값
	 * @return
	 * 	최종 결과값 (Int)
	 */
	private static int parseInt(Object value, int defaultValue) {
        try {
            return value != null ? Integer.parseInt(value.toString()) : defaultValue;
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

}
