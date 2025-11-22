package com.routy.routyback.service.user;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.routy.routyback.common.ApiResponse;
import com.routy.routyback.common.ParamProcessor;
import com.routy.routyback.dto.ProductUserDTO;
import com.routy.routyback.mapper.user.ProductUserMapper;

@Service
public class ProductUserService implements IProductUserService{
	
	@Autowired
	ProductUserMapper productUserMapper;
	
	
	@Override
	public ProductUserDTO productDetailView(int prdNo) { //사용자 제품 상세조회
		return productUserMapper.productDetailView(prdNo);
	}

	@Override
	public ApiResponse productAllSkinCate(Map<String, Object> params) { // 피부타입별 추천 제품목록
		try {
			params.put("limit", ParamProcessor.parseInt(params.get("limit"), 1));
			
			return ApiResponse.success(productUserMapper.productAllSkinCate(params));
		} catch (Exception e) {
			return ApiResponse.fromException(e);
		}
	}

	@Override
	public ApiResponse productAllSkinCommend(Map<String, Object> params) { // 당신을 위한 맞춤 추천
		try {
			Object userNo = params.get("user_no");
			List<Integer> cateList = Arrays.asList(11001, 13001, 11003, 11005, 13005);
			
			// 카테고리값 받기
			if(userNo != null) {
				List<Integer> selectCateList = productUserMapper.productCateViewed(params);
				if(selectCateList.size() == 5) cateList = selectCateList;
			}
			
			params.put("limit", ParamProcessor.parseInt(params.get("limit"), 1));
			params.remove("skin");
			List<ProductUserDTO> resultData = new ArrayList<>();
			
			for(Integer cate : cateList) {
				params.put("sub_cate", cate);
				List<ProductUserDTO> tempData = productUserMapper.productAllSkinCate(params);
				
				resultData.addAll(tempData);
			}
			
			return ApiResponse.success(resultData);
		} catch (Exception e) {
			return ApiResponse.fromException(e);
		}
	}

}
