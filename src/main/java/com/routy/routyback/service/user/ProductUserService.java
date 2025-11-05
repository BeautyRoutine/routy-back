package com.routy.routyback.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

}
