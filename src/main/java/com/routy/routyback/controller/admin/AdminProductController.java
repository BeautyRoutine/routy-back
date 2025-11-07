package com.routy.routyback.controller.admin;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.routy.routyback.dto.ProductDTO;
import com.routy.routyback.service.admin.IProductService;

@RestController
@RequestMapping("/api/admin/products")
public class AdminProductController {

	@Autowired
	private IProductService productService;

	@PostMapping
	public ResponseEntity<?> create(@RequestBody ProductDTO product) {
		productService.insertProduct(product);
		return ResponseEntity.status(HttpStatus.CREATED).body("상품 등록 완료");
	}

	@GetMapping
	public ResponseEntity<ArrayList<ProductDTO>> getAll() {
		ArrayList<ProductDTO> list = productService.getAll();
		return ResponseEntity.ok(list);
	}

	@GetMapping("/{prdNo}")
	public ResponseEntity<ProductDTO> getOne(@PathVariable int prdNo) {
		ProductDTO product = productService.getById(prdNo);

		if (product == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		return ResponseEntity.ok(product);
	}

	@PutMapping("/{prdNo}")
	public ResponseEntity<?> update(@PathVariable int prdNo, @RequestBody ProductDTO product) {
		product.setPrdNo(prdNo);
		productService.updateProduct(product);
		return ResponseEntity.ok("상품 수정 완료");
	}

	@DeleteMapping("/{prdNo}")
	public ResponseEntity<?> delete(@PathVariable int prdNo) {
		productService.deleteProduct(prdNo);
		return ResponseEntity.ok("상품 삭제 완료");
	}

	@PutMapping("/{prdNo}/stock")
	public ResponseEntity<?> updateStock(@PathVariable int prdNo, @RequestParam("amount") int amount) {
		try {
			productService.updateStock(prdNo, amount);
			return ResponseEntity.ok("재고 업데이트 완료");
		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}

	@PutMapping("/{prdNo}/status")
	public ResponseEntity<?> updateStatus(@PathVariable int prdNo, @RequestParam("status") String status) {

		productService.updateStatus(prdNo, status);
		return ResponseEntity.ok("상태 업데이트 완료");
	}
}
