package com.routy.routyback.controller.admin;

import com.routy.routyback.dto.ProductDTO;
import com.routy.routyback.service.admin.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/products")
public class AdminProductController {

    @Autowired
    private IProductService productService;

    // ⭐ 검색 + 페이징
    @GetMapping
    public ResponseEntity<?> getProductList(
            @RequestParam(value = "prd_name", required = false) String prdName,
            @RequestParam(value = "prd_company", required = false) String prdCompany,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int pageGap
    ) {
        Map<String, Object> params = new HashMap<>();
        params.put("prdName", prdName);
        params.put("prdCompany", prdCompany);
        params.put("page", page);
        params.put("pageGap", pageGap);

        return ResponseEntity.ok(productService.getList(params));
    }


    // 단건 조회
    @GetMapping("/{prdNo}")
    public ResponseEntity<?> getOne(@PathVariable int prdNo) {
        ProductDTO dto = productService.getById(prdNo);
        if (dto == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("상품 없음");
        return ResponseEntity.ok(dto);
    }

    // 등록
    @PostMapping
    public ResponseEntity<?> create(@RequestBody ProductDTO dto) {
        productService.insertProduct(dto);
        return ResponseEntity.ok("등록 완료");
    }

    // 수정
    @PutMapping
    public ResponseEntity<?> update(@RequestBody ProductDTO dto) {
        productService.updateProduct(dto);
        return ResponseEntity.ok("수정 완료");
    }

    // 삭제
    @DeleteMapping("/{prdNo}")
    public ResponseEntity<?> delete(@PathVariable int prdNo) {
        productService.deleteProduct(prdNo);
        return ResponseEntity.ok("삭제 완료");
    }

    // 재고
    @PutMapping("/{prdNo}/stock")
    public ResponseEntity<?> updateStock(@PathVariable int prdNo, @RequestParam int stock) {
        productService.updateStock(prdNo, stock);
        return ResponseEntity.ok("재고 수정 완료");
    }

    // 상태
    @PutMapping("/{prdNo}/status")
    public ResponseEntity<?> updateStatus(@PathVariable int prdNo, @RequestParam String status) {
        productService.updateStatus(prdNo, status);
        return ResponseEntity.ok("상태 수정 완료");
    }
}
