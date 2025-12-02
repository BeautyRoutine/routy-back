package com.routy.routyback.service.user;

import com.routy.routyback.dto.user.IngredientPreferenceResponse;
import java.util.List;
import java.util.Map;

public interface IUserIngredientService {

    Map<String, List<IngredientPreferenceResponse>> getUserIngredients(Long userNo);

    void addIngredient(Long userNo, Long ingredientId, String type);

    void removeIngredient(Long userNo, Long ingredientId, String type);
}