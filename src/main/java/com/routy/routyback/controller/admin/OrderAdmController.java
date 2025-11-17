package com.routy.routyback.controller.admin;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.routy.routyback.common.ApiResponse;
import com.routy.routyback.service.admin.OrderAdmService;

@RestController
public class OrderAdmController {
	@Autowired
	OrderAdmService service;
	
	@GetMapping("/api/orders/list")
	public ApiResponse listAllOrders(@RequestParam Map<String, Object> params) {
		return service.listAllOrders(params);
	}
	@GetMapping("/api/orders/detail/{odNo}")
	public ApiResponse listAllOrders(@PathVariable int odNo) {
		return service.detailOrder(odNo);
	}
}
