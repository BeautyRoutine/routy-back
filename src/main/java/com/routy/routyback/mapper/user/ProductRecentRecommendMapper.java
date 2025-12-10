package com.routy.routyback.mapper.user;

import com.routy.routyback.dto.ProductRecentRecommendDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ProductRecentRecommendMapper {

    // 가장 최근 본 소카테고리 1개
    Integer findLatestSubcate(@Param("userNo") Integer userNo);

    // 최근 본 카테고리 기반 추천 4개
    List<ProductRecentRecommendDTO> recommendByLatestSubcate(
            @Param("userNo") Integer userNo,
            @Param("subcate") Integer subcate
    );
}
