package com.routy.routyback.dto.user;

import java.util.List;

import lombok.Data;

@Data
public class ProductIngredientAnalysisDTO {
	 // 피부타입 분석
    private List<String> goodEffects;
    private List<String> badEffects;
    
    // 즐겨찾는 성분
    private List<String> myFavoriteIngredients;
    //싫어하는 성분
    private List<String> myAvoidIngredients;
    
    // 알레르기 체크
    private List<String> allergenIngredients;
    
    // 주의성분 체크
    private List<String> dangerIngredients;
    
    // 총 성분 수, 전체 성분 리스트
    private int totalCount;
    private List<IngredientSimpleDTO> allIngredients;
}
