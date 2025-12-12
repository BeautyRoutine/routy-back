package com.routy.routyback.service.admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.routy.routyback.dto.IngredientDTO;
import com.routy.routyback.mapper.admin.IngredientMapper;

@Service
public class IngredientService implements IIngredientService {

    @Autowired
    IngredientMapper ingredientMapper;

    @Override
    public ArrayList<IngredientDTO> getAllIngredients() {
        return ingredientMapper.getAllIngredients();
    }

    @Override
    public IngredientDTO getIngredientByNo(int ingNo) {
        return ingredientMapper.getIngredientByNo(ingNo);
    }

    @Override
    public void insertIngredient(IngredientDTO ingredient) {
        ingredientMapper.insertIngredient(ingredient);
    }

    @Override
    public void updateIngredient(IngredientDTO ingredient) {
        ingredientMapper.updateIngredient(ingredient);
    }

    @Override
    public void deleteIngredient(int ingNo) {
        ingredientMapper.deleteIngredient(ingNo);
    }

    /**
     * ⭐ 검색 + 페이징 목록 조회
     */
    @Override
    public Map<String, Object> getIngredientList(int page, int pageGap, String ingName, String ingAllergen) {

        // Oracle ROWNUM 페이징 계산
        int start = (page - 1) * pageGap + 1;
        int end = page * pageGap;

        Map<String, Object> params = new HashMap<>();
        params.put("start", start);
        params.put("end", end);
        params.put("ingName", ingName);
        params.put("ingAllergen", ingAllergen);

        // 목록 조회
        List<IngredientDTO> list = ingredientMapper.searchIngredients(params);

        // 전체 개수 조회
        int total = ingredientMapper.countIngredients(params);

        // 프론트에서 요구하는 구조 반환
        Map<String, Object> result = new HashMap<>();
        result.put("list", list);
        result.put("total", total);

        return result;
    }
}
