package com.routy.routyback.controller.user;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.routy.routyback.common.ApiResponse;
import com.routy.routyback.service.user.IngredientUserService;

@RestController
public class IngredientUserController {
	@Autowired
	IngredientUserService service;
	
	@GetMapping("/api/ingredient/effect/prd_skin")
	public ApiResponse effectAllPrdSkin(@RequestParam Map<String, Object> params) { // 제품&피부타입 데이터 기반 추천/주의 성분 효과
		return service.effectAllPrdSkin(params);
	}
}
