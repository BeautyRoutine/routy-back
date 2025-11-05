package com.routy.routyback.service;

import com.routy.routyback.dto.IngredientDTO;

public interface IIngredientService {
	void insertIngredient(IngredientDTO vo);

	void updateIngredient(IngredientDTO vo);

	void deleteIngredient(int id);
}
