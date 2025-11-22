package com.routy.routyback.service.user;

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
	public ProductUserDTO productDetailView(int prdNo) {
		// TODO Auto-generated method stub
		return productUserMapper.productDetailView(prdNo);
	}


	@Override
	public ApiResponse productAllSkinCate(Map<String, Object> params) {
		try {
			params.put("limit", ParamProcessor.parseInt(params.get("limit"), 1));
			
			return ApiResponse.success(productUserMapper.productAllSkinCate(params));
		} catch (Exception e) {
			return ApiResponse.fromException(e);
		}
	}

}
