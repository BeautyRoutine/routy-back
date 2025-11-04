package com.routy.routyback.service;

import com.routy.routyback.domain.IngredientVO;

public interface IIngredientService {
	void insertIngredient(IngredientVO vo);

	void updateIngredient(IngredientVO vo);

	void deleteIngredient(int id);
}
