package com.routy.routyback.mapper.user;

import java.util.List;
import java.util.Map;


import com.routy.routyback.dto.user.IngredientSimpleDTO;

public interface IIngredientUserMapper {

	/**
	 * 제품번호, 피부타입 데이터를 받고 피부타입에 추천 성분과 효과 데이터를 반환
	 * @param params
	 * 	검색조건(prdNo:제품번호, userSkin:사용자 피부타입)
	 * @return 추천/주의 성분과 효과 (List)
	 */
	List<Map<String, Object>> effectGoodPrdSkin(Map<String, Object> params);
	/**
	 * 제품번호, 피부타입 데이터를 받고 피부타입에 주의 성분과 효과 데이터를 반환
	 * @param params
	 * 	검색조건(prdNo:제품번호, userSkin:사용자 피부타입)
	 * @return 추천/주의 성분과 효과 (List)
	 */
	List<Map<String, Object>> effectBadPrdSkin(Map<String, Object> params);
	
	//전체 성분표 가져오기
	List<IngredientSimpleDTO> selectIngredientsByPrdNo(int prdNo);
	//성분 즐겨찾기 성분 비교
	List<String> selectMatchingFavoriteIngredients(Map<String, Object> params);
}
