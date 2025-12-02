package com.routy.routyback.service.user.mypage;

import com.routy.routyback.dto.user.mypage.IngredientPreferenceResponse;
import com.routy.routyback.mapper.user.mypage.UserIngredientMapper;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserIngredientService implements IUserIngredientService {

    // 사용자 선호/기피 성분 기능을 처리하는 매퍼
    private final UserIngredientMapper userIngredientMapper;

    /**
     * 사용자 선호/기피 성분 목록을 조회합니다.
     * @param userNo 사용자 번호
     * @return focus/avoid 리스트를 포함한 Map
     */
    @Override
    public Map<String, List<IngredientPreferenceResponse>> getUserIngredients(Long userNo) {
        List<IngredientPreferenceResponse> focus = userIngredientMapper.selectFocusIngredients(userNo);
        List<IngredientPreferenceResponse> avoid = userIngredientMapper.selectAvoidIngredients(userNo);

        Map<String, List<IngredientPreferenceResponse>> result = new HashMap<>();
        result.put("focus", focus);
        result.put("avoid", avoid);

        return result;
    }

    /**
     * 사용자에게 성분을 추가합니다. 타입에 따라 선호/기피로 저장합니다.
     * @param userNo 사용자 번호
     * @param ingredientId 성분 번호
     * @param type FOCUS 또는 AVOID
     */
    @Override
    public void addIngredient(Long userNo, Long ingredientId, String type) {
        int typeCode = type.equalsIgnoreCase("FOCUS") ? 1 : 2;

        if (userIngredientMapper.countIngredient(userNo, ingredientId, typeCode) == 0) {
            userIngredientMapper.insertIngredient(userNo, ingredientId, typeCode);
        }
    }

    /**
     * 사용자에게 등록된 성분을 삭제합니다.
     * @param userNo 사용자 번호
     * @param ingredientId 성분 번호
     * @param type FOCUS 또는 AVOID
     */
    @Override
    public void removeIngredient(Long userNo, Long ingredientId, String type) {
        int typeCode = type.equalsIgnoreCase("FOCUS") ? 1 : 2;
        userIngredientMapper.deleteIngredient(userNo, ingredientId, typeCode);
    }
}