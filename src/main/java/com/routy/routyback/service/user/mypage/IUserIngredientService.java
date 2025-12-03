package com.routy.routyback.service.user.mypage;

import com.routy.routyback.dto.user.mypage.IngredientPreferenceResponse;
import java.util.List;
import java.util.Map;

public interface IUserIngredientService {

    Map<String, List<IngredientPreferenceResponse>> getUserIngredients(String userId);

    void addIngredient(String userId, Long ingredientId, String type);

    void removeIngredient(String userId, Long ingredientId, String type);
}