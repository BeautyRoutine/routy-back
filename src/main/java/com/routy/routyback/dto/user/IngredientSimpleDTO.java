package com.routy.routyback.dto.user;

import lombok.Data;

@Data
public class IngredientSimpleDTO {
	private int ingNo;
    private String ingName;      // 성분명
    private String ingEngName;   // 영문명
    private int ingAllergen;     // 알레르기 여부 (1:주의, 0:안전)
    private int ingDanger;       // 위험 성분 여부 (1:주의, 0:안전)
    private String ingFunctional; // 기능성 내용
}
