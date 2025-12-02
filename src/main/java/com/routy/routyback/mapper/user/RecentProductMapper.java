package com.routy.routyback.mapper.user.mypage;

import com.routy.routyback.dto.user.mypage.RecentProductResponse;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 최근 본 상품 조회 매퍼
 */
@Mapper
public interface RecentProductMapper {

    List<RecentProductResponse> getRecentViewedProducts(@Param("userNo") Long userNo);
}