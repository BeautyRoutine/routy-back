package com.routy.routyback.mapper.user;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.routy.routyback.dto.ProductUserDTO;

@Mapper
public interface ProductUserMapper {
	
	ProductUserDTO productDetailView(@Param("prdNo") int prdNo); //제품 상세조회

}
