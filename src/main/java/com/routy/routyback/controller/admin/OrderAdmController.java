package com.routy.routyback.controller.admin;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.routy.routyback.service.admin.OrderAdmService;

@RestController
public class OrderAdmController {
	@Autowired
	OrderAdmService service;
	
	@GetMapping("/orders/list")
	public List<Map<String, Object>> listAllOrders(@RequestParam Map<String, Object> params) {
		return service.listAllOrders(params);
	}
}
