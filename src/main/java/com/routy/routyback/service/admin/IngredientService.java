package com.routy.routyback.service.admin;

import java.util.ArrayList;

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
		// TODO 성분 조회
		return ingredientMapper.getAllIngredients();
	}

	@Override
	public IngredientDTO getIngredientByNo(int ingNo) {
		// TODO 성분 상세 조회
		return ingredientMapper.getIngredientByNo(ingNo);
	}

	@Override
	public void insertIngredient(IngredientDTO ingredient) {
		// TODO 성분 추가
		ingredientMapper.insertIngredient(ingredient);
	}

	@Override
	public void updateIngredient(IngredientDTO ingredient) {
		// TODO 성분 수정
		ingredientMapper.updateIngredient(ingredient);
	}

	@Override
	public void deleteIngredient(int ingNo) {
		// TODO 성분 삭제 
		ingredientMapper.deleteIngredient(ingNo);
	}

}
