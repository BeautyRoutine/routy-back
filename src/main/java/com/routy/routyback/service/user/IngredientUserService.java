package com.routy.routyback.service.user;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.routy.routyback.common.ApiResponse;
import com.routy.routyback.dto.user.IngredientSimpleDTO;
import com.routy.routyback.dto.user.ProductIngredientAnalysisDTO;
import com.routy.routyback.mapper.user.IIngredientUserMapper;

@Service
public class IngredientUserService implements IIngredientUserService {
	@Autowired
	@Qualifier("IIngredientUserMapper")
	IIngredientUserMapper mapper;

	 public ApiResponse getProductAnalysis(int prdNo, String userId, String userSkin) {
	        try {
	            ProductIngredientAnalysisDTO result = new ProductIngredientAnalysisDTO();
	            Map<String, Object> params = new HashMap<>();
	            params.put("prdNo", prdNo);
	            params.put("userSkin", userSkin);
	            params.put("userId", userId);

	            if (userSkin != null && !userSkin.isEmpty() && !userSkin.equals("null")) {
	            // 피부타입 분석 
	            List<Map<String, Object>> goodMap = mapper.effectGoodPrdSkin(params);
	            List<Map<String, Object>> badMap = mapper.effectBadPrdSkin(params);
	            
	            // Map에서 이름만 뽑아서 리스트로 변환
	            result.setGoodEffects(goodMap.stream().map(m -> (String)m.get("EFFNAME")).distinct().toList());
	            result.setBadEffects(badMap.stream().map(m -> (String)m.get("EFFNAME")).distinct().toList());
	            } else {
	                // 피부타입이 없으면 빈 리스트 처리 (DB 에러 방지)
	                result.setGoodEffects(new ArrayList<>());
	                result.setBadEffects(new ArrayList<>());
	            }
	            
	            // 즐겨찾는/싫어하는 성분 확인
	            if  (userId != null && !userId.isEmpty() && !userId.equals("null")) {
	                result.setMyFavoriteIngredients(mapper.selectMatchingFavoriteIngredients(params));
	                result.setMyAvoidIngredients(mapper.selectMatchingAvoidIngredients(params));
	            } else {
	                result.setMyFavoriteIngredients(new ArrayList<>());
	                result.setMyAvoidIngredients(new ArrayList<>());
	            }

	            //전체 성분 가져오기
	            List<IngredientSimpleDTO> allIngredients = mapper.selectIngredientsByPrdNo(prdNo);
	            result.setAllIngredients(allIngredients);
	            result.setTotalCount(allIngredients.size()); // 총 성분 수

	            // 알레르기 및 주의성분 분류
	            List<String> allergens = new ArrayList<>();
	            List<String> dangers = new ArrayList<>();

	            for (IngredientSimpleDTO ing : allIngredients) {
	                // INGREDIENTS 테이블 INGALLERGEN 컬럼이 1이면
	                if (ing.getIngAllergen() == 1) {
	                    allergens.add(ing.getIngName());
	                }
	                // INGREDIENTS 테이블 INGDANGER 컬럼이 1이면
	                if (ing.getIngDanger() == 1) {
	                    dangers.add(ing.getIngName());
	                }
	            }

	            result.setAllergenIngredients(allergens);
	            result.setDangerIngredients(dangers);

	            return ApiResponse.success(result);

	        } catch (Exception e) {
	            e.printStackTrace();
	            return ApiResponse.fromException(e); 
	        }
	    }
	 
	 @Override
		public ApiResponse effectAllPrdSkin(Map<String, Object> params) {
			try {
				List<Map<String, Object>> good = mapper.effectGoodPrdSkin(params);
				List<Map<String, Object>> bad = mapper.effectBadPrdSkin(params);
				
				Map<String, Object> result = new java.util.HashMap<>();
				result.put("good", good);
		        result.put("bad", bad);
		        
				return ApiResponse.success(result);
			} catch (Exception e) {
				return ApiResponse.fromException(e);
			}
	 	}
}
