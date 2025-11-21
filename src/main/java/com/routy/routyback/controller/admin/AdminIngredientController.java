package com.routy.routyback.controller.admin;

import com.routy.routyback.dto.IngredientDTO;
import com.routy.routyback.service.admin.IIngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/admin/ingredients")
public class AdminIngredientController {

    @Autowired
    private IIngredientService ingredientService;

    // 검색 + 페이징 조회
    @GetMapping
    public ResponseEntity<?> getIngredientList(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int page_gap,
            @RequestParam(required = false) String ing_name,
            @RequestParam(required = false) String ing_allergen
    ) {
        Map<String, Object> result =
                ingredientService.getIngredientList(page, page_gap, ing_name, ing_allergen);

        return ResponseEntity.ok(result);
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
