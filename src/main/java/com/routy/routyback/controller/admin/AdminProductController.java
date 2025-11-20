package com.routy.routyback.controller.admin;

import java.io.File;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.routy.routyback.dto.ProductDTO;
import com.routy.routyback.service.admin.IProductService;
import org.springframework.web.multipart.MultipartFile;

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
    public ResponseEntity<?> getAll(
            @RequestParam(required = false) String prd_name,
            @RequestParam(required = false) String prd_company
    ) {
        ArrayList<ProductDTO> list = productService.getAll(prd_name, prd_company);

        Map<String, Object> result = new HashMap<>();
        result.put("list", list);
        result.put("total", list.size());

        return ResponseEntity.ok(result);
    }


    @GetMapping("/{prdNo}")
    public ResponseEntity<ProductDTO> getOne(@PathVariable int prdNo) {
        ProductDTO product = productService.getById(prdNo);

        if (product == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(product);
    }

    @PutMapping(value = "/{prdNo}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> update(
            @PathVariable int prdNo,
            @ModelAttribute ProductDTO product,
            @RequestPart(value = "prdImg", required = false) MultipartFile file
    ) {

        try {
            // 상품번호 세팅
            product.setPrdNo(prdNo);

            // 이미지 수정이 들어온 경우
            if (file != null && !file.isEmpty()) {
                String fileName = file.getOriginalFilename();
                product.setPrdImg(fileName);

                // 파일 저장 경로 (수정 필요 시 알려줘)
                String uploadPath = "C:/upload/product/";

                File dest = new File(uploadPath + fileName);
                file.transferTo(dest);
            }

            // DB 업데이트 실행
            productService.updateProduct(product);

            return ResponseEntity.ok("상품 수정 완료");

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("상품 수정 실패");
        }
    }

    @DeleteMapping("/{prdNo}")
    public ResponseEntity<?> delete(@PathVariable int prdNo) {
        productService.deleteProduct(prdNo);
        return ResponseEntity.ok("상품 삭제 완료");
    }

    @PutMapping("/{prdNo}/stock")
    public ResponseEntity<?> updateStock(@PathVariable int prdNo, @RequestParam("stock") int stock) {
        try {
            productService.updateStock(prdNo, stock);
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
