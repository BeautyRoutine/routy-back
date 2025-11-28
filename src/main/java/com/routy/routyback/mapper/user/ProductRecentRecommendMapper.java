package com.routy.routyback.mapper.user;

import com.routy.routyback.dto.ProductRecentRecommendDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductRecentRecommendMapper {
    List<ProductRecentRecommendDTO> selectRecommendedProducts(
            @Param("userNo") Integer userNo,
            @Param("recent") List<Integer> recent,
            @Param("subcate") Integer subcate
    );
}
