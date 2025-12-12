package com.routy.routyback.mapper.admin;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.routy.routyback.dto.IngredientDTO;

@Mapper
public interface IngredientMapper {

    // 전체 성분 조회
    ArrayList<IngredientDTO> getAllIngredients();

    // 성분 단일 조회
    IngredientDTO getIngredientByNo(int ingNo);

    // 성분 추가
    void insertIngredient(IngredientDTO ingredient);

    // 성분 수정
    void updateIngredient(IngredientDTO ingredient);

    // 성분 삭제
    void deleteIngredient(int ingNo);

    // 성분 검색 + 페이징 목록 조회
    List<IngredientDTO> searchIngredients(Map<String, Object> params);

    // 성분 검색 + 전체 개수 조회
    int countIngredients(Map<String, Object> params);
}
