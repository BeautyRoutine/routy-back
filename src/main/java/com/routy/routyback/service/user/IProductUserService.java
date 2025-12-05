package com.routy.routyback.service.user;


import java.util.List;
import java.util.Map;

import com.routy.routyback.common.ApiResponse;
import com.routy.routyback.dto.ProductUserDTO;

public interface IProductUserService {

	ProductUserDTO productDetailView(int prdNo); //사용자 제품 상세조회
	ApiResponse productAllSkinCate(Map<String, Object> params); // 피부타입별 추천 제품목록
	ApiResponse productAllSkinCommend(Map<String, Object> params); // 당신을 위한 맞춤 추천
    ApiResponse productPopular(Map<String, Object> params);
    ApiResponse productSkinBased(Map<String, Object> params);
    ApiResponse getFallbackProducts(int limit);
    ApiResponse getProductList(Map<String, Object> params);

}
