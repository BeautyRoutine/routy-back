package com.routy.routyback.mapper.user;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.routy.routyback.dto.RecommendedProductDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.routy.routyback.domain.ProductImageVO;
import com.routy.routyback.dto.ProductUserDTO;

@Mapper
public interface ProductUserMapper {
	
	ProductUserDTO productDetailView(@Param("prdNo") int prdNo); //제품 상세조회
	List<ProductImageVO> selectProductImageList(@Param("prdNo") int prdNo); //이미지 리스트 조회
	
	/**
	 * 피부타입값을 받아 해당 피부타입 사람들이 가장 많이 긍정적인 반응을 보인 제품 목록을 받아옴
	 * @param params
	 * 	검색선택조건(skin:피부타입, limit:받아올 제품 갯수, sub_cate: 검색할 카테고리 범위)
	 * @return 추천 제품 (List)
	 */
	ArrayList<ProductUserDTO> productAllSkinCate(Map<String, Object> params);
	
	/**
	 * 당신을 위한 맞춤 추천 - 카테고리 가져오기
	 * @param params
	 * 	검색필수조건(user_no:회원번호)
	 * @return 카테고리 (List)
	 */
	List<Integer> productCateViewed(Map<String, Object> params);

	

}
