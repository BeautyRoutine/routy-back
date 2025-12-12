package com.routy.routyback.controller.user;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.routy.routyback.common.ApiResponse;
import com.routy.routyback.service.user.CategoriesService;

@RestController
@RequestMapping("/api/categories")
public class CategoriesController {
	@Autowired
	CategoriesService service;
	
	@GetMapping("/main")
	public ApiResponse listMainCategories(){ // 메인 카테고리 받아오기
		return service.listMainCategories();
	}
	@GetMapping("/sub")
	public ApiResponse listSubCategories(@RequestParam Map<String, Object> params){ // 서브 카테고리 받아오기
		return service.listSubCategories(params);
	}

}
