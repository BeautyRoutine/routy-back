package com.routy.routyback.dto.user.mypage;

import lombok.Data;

/**
 * 사용자 선호/기피 성분 조회 응답 DTO
 */
@Data
public class IngredientPreferenceResponse {

    private Long ingredientId;     // INGNO
    private String name;           // INGNAME
    private String engName;        // INGENNAME
    private String description;    // INGDESC
    private String functional;     // INGFUNCTIONAL
    private Integer allergen;      // INGALLERGEN
    private Integer danger;        // INGDANGER
    private String addedDate;      // 등록일
}