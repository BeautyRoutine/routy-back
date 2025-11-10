package com.routy.routyback.controller.admin;

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

import com.routy.routyback.dto.IngredientDTO;
import com.routy.routyback.service.admin.IIngredientService;

@RestController
@RequestMapping("/api/products/ingredients") //매핑 어디로 할까요???
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

    // 등록
    @PostMapping
    public ResponseEntity<?> insertIngredient(@RequestBody IngredientDTO ingredient) {
        ingredientService.insertIngredient(ingredient);
        return ResponseEntity.ok("성분 등록 완료");
    }

    // 수정
    @PutMapping
    public ResponseEntity<?> updateIngredient(@RequestBody IngredientDTO ingredient) {
        ingredientService.updateIngredient(ingredient);
        return ResponseEntity.ok("성분 수정 완료");
    }

    // 삭제
    @DeleteMapping("/{ingNo}")
    public ResponseEntity<?> deleteIngredient(@PathVariable int ingNo) {
        ingredientService.deleteIngredient(ingNo);
        return ResponseEntity.ok("성분 삭제 완료");
    }
}

