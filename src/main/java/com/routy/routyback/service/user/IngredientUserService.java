package com.routy.routyback.service.user;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.routy.routyback.common.ApiResponse;
import com.routy.routyback.mapper.user.IIngredientUserMapper;

@Service
public class IngredientUserService implements IIngredientUserService {
	@Autowired
	@Qualifier("IIngredientUserMapper")
	IIngredientUserMapper mapper;

	@Override
	public ApiResponse effectAllPrdSkin(Map<String, Object> params) {
		try {
			List<Map<String, Object>> good = mapper.effectGoodPrdSkin(params);
			List<Map<String, Object>> bad = mapper.effectBadPrdSkin(params);
			
			Map<String, Object> result = new java.util.HashMap<>();
			result.put("good", good);
	        result.put("bad", bad);
	        
			return ApiResponse.success(result);
		} catch (Exception e) {
			return ApiResponse.fromException(e);
		}
	}

}
