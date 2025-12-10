package com.routy.routyback.mapper.user;

import com.routy.routyback.dto.ProductRecentRecommendDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ProductRecentRecommendMapper {

    // 최신 subcate 조회
    Integer findLatestSubcate(@Param("userNo") Integer userNo);

    // 추천 상품 조회
    List<ProductRecentRecommendDTO> recommendByLatestSubcate(
            @Param("userNo") Integer userNo,
            @Param("subcate") Integer subcate
    );
}
