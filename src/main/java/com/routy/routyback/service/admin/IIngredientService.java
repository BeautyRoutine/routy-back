package com.routy.routyback.service.admin;

import java.util.ArrayList;

import com.routy.routyback.dto.IngredientDTO;


public interface IIngredientService {
	
	ArrayList<IngredientDTO> getAllIngredients();
	
	IngredientDTO getIngredientByNo(int ingNo);
	
	void insertIngredient(IngredientDTO ingredient);

	void updateIngredient(IngredientDTO ingredient);

	void deleteIngredient(int ingNo);
}
