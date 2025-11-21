package com.routy.routyback.service.admin;

import java.util.ArrayList;
import java.util.Map;

import com.routy.routyback.dto.IngredientDTO;

public interface IIngredientService {

    // 전체 조회 (백오피스에서 선택적으로 사용 가능)
    ArrayList<IngredientDTO> getAllIngredients();

    // 단일 조회
    IngredientDTO getIngredientByNo(int ingNo);

    // 성분 추가
    void insertIngredient(IngredientDTO ingredient);

    // 성분 수정
    void updateIngredient(IngredientDTO ingredient);

    // 성분 삭제
    void deleteIngredient(int ingNo);

    // ⭐ 검색 + 페이징 목록 조회 (프론트에서 사용)
    Map<String, Object> getIngredientList(
            int page,
            int pageGap,
            String ingName,
            String ingAllergen
    );
}
