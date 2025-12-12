package com.routy.routyback.controller.admin;

import com.routy.routyback.service.admin.IIngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/products/{prdNo}/ingredients")
public class IngredientController {

    @Autowired
    private IIngredientService ingredientService;

    // 전체 조회
    @GetMapping
    public ResponseEntity<?> getAllIngredients() {
        return ResponseEntity.ok(ingredientService.getAllIngredients());
    }

    // 단일 조회
    @GetMapping("/{ingNo}")
    public ResponseEntity<?> getIngredientByNo(@PathVariable int ingNo) {
        return ResponseEntity.ok(ingredientService.getIngredientByNo(ingNo));
    }
}
