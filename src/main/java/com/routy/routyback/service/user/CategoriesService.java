package com.routy.routyback.service.user;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.routy.routyback.common.ApiResponse;
import com.routy.routyback.common.ParamProcessor;
import com.routy.routyback.common.category.Category;
import com.routy.routyback.common.category.CategoryRepository;

@Service
public class CategoriesService {
	@Autowired
	CategoryRepository cateRepo;
	
	public ApiResponse listMainCategories() {
		try {
			Map<Integer, Category> resultRow = cateRepo.getMainCate();
			
			return ApiResponse.success(resultRow);
		} catch (Exception e) {
			return ApiResponse.fromException(e);
		}
	}

	public ApiResponse listSubCategories(Map<String, Object> params) {
		try {
			int mainCate = ParamProcessor.parseInt(params.get("main_cate"), 0);
			Map<Integer, String> resultRow = cateRepo.getSubCate(mainCate);
			
			return ApiResponse.success(resultRow);
		} catch (Exception e) {
			return ApiResponse.fromException(e);
		}
	}
}
