package com.routy.routyback.mapper.admin;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;

import com.routy.routyback.dto.IngredientDTO;

@Mapper
public interface IngredientMapper {
	ArrayList<IngredientDTO> getAllIngredients();
	IngredientDTO getIngredientByNo(int ingNo);
	void insertIngredient(IngredientDTO ingredient);
	void updateIngredient(IngredientDTO ingredient);
	void deleteIngredient(int ingNo);
}
