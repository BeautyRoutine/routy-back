package com.routy.routyback.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.routy.routyback.domain.ProductVO;
import com.routy.routyback.service.IProductService;

@RestController
@RequestMapping("/admin/products")
public class AdminProductController {

	@Autowired
	private IProductService productService;

	@PostMapping
	public ResponseEntity<?> create(@RequestBody ProductVO product) {
		productService.insertProduct(product);
		return ResponseEntity.ok("등록 완료");
	}

	@PutMapping("/{prdNo}")
	public ResponseEntity<?> update(@PathVariable int prdNo, @RequestBody ProductVO product) {
		product.setPrdNo(prdNo);
		productService.updateProduct(product);
		return ResponseEntity.ok("수정 완료");
	}

	@DeleteMapping("/{prdNo}")
	public ResponseEntity<?> delete(@PathVariable int prdNo) {
		productService.deleteProduct(prdNo);
		return ResponseEntity.ok("삭제 완료");
	}

	@GetMapping
	public ArrayList<ProductVO> getAll() {
		return productService.getAll();
	}

	@GetMapping("/{prdNo}")
	public ProductVO getOne(@PathVariable int prdNo) {
		return productService.getById(prdNo);
	}
}