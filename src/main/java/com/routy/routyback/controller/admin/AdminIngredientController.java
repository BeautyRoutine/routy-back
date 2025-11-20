package com.routy.routyback.controller.admin;

import com.routy.routyback.dto.IngredientDTO;
import com.routy.routyback.service.admin.IIngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/ingredients")
public class AdminIngredientController {

    @Autowired
    private IIngredientService ingredientService;

    @GetMapping
    public ResponseEntity<?> getAllIngredients() {
        return ResponseEntity.ok(ingredientService.getAllIngredients());
    }

    @GetMapping("/{ingNo}")
    public ResponseEntity<?> getIngredientByNo(@PathVariable int ingNo) {
        return ResponseEntity.ok(ingredientService.getIngredientByNo(ingNo));
    }

    @PostMapping
    public ResponseEntity<?> insertIngredient(@RequestBody IngredientDTO ingredient) {
        ingredientService.insertIngredient(ingredient);
        return ResponseEntity.ok("성분 등록 완료");
    }

    @PutMapping
    public ResponseEntity<?> updateIngredient(@RequestBody IngredientDTO ingredient) {
        ingredientService.updateIngredient(ingredient);
        return ResponseEntity.ok("성분 수정 완료");
    }

    @DeleteMapping("/{ingNo}")
    public ResponseEntity<?> deleteIngredient(@PathVariable int ingNo) {
        ingredientService.deleteIngredient(ingNo);
        return ResponseEntity.ok("성분 삭제 완료");
    }
}
