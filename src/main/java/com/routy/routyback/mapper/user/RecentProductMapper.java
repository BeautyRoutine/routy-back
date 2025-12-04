package com.routy.routyback.mapper.user;

import com.routy.routyback.dto.user.RecentProductResponse;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface RecentProductMapper {

    // =========================================
    // 최근 본 상품 존재 여부 확인 / INSERT / UPDATE / 삭제 제한 (10개)
    // XML: com.routy.routyback.mapper.user.RecentProductMapper
    // =========================================

    // 특정 사용자가 특정 상품을 이미 본 적 있는지 개수 조회 (0 또는 1 예상)
    int exists(@Param("userNo") Long userNo,
        @Param("prdNo") Long prdNo);

    // 최근 본 상품 새로 등록 (처음 보는 상품일 때만 사용)
    void insertRecentProduct(@Param("userNo") Long userNo,
        @Param("prdNo") Long prdNo,
        @Param("prdSubCate") Long prdSubCate);

    // 이미 본 상품일 경우, 조회 시간(VPREGDATE)만 현재 시간으로 갱신
    void updateViewTime(@Param("userNo") Long userNo,
        @Param("prdNo") Long prdNo);

    // 특정 사용자의 최근 본 상품 목록 조회 (최신순, 최대 10개)
    List<RecentProductResponse> getRecentViewedProducts(@Param("userNo") Long userNo);

    // 특정 사용자의 최근 본 상품이 10개를 초과할 경우, 오래된 데이터 삭제
    void trimRecentProducts(@Param("userNo") Long userNo);

    // userId(loginId)로 userNo(PK) 조회
    Long getUserNoByUserId(@Param("userId") String userId);
}