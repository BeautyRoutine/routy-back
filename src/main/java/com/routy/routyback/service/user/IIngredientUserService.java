package com.routy.routyback.service.user;

import java.util.Map;

import com.routy.routyback.common.ApiResponse;

public interface IIngredientUserService {
	ApiResponse effectAllPrdSkin(Map<String, Object> params); // 제품&피부타입 데이터 기반 추천/주의 성분 효과
}
