package com.routy.routyback.service.user.mypage;

import com.routy.routyback.dto.user.RecentProductResponse;
import java.util.List;

/**
 * 최근 본 상품 서비스 인터페이스
 * - 상품 조회 시 최근 본 상품 등록
 * - 최근 본 상품 목록 조회 (최신순 10개)
 */
public interface IRecentProductService {

    /**
     * 특정 사용자의 최근 본 상품을 추가 또는 갱신
     * - 이미 본 상품이면 조회 시간만 갱신
     * - 처음 보는 상품이면 새로 등록
     * - 각 사용자별 최대 10개까지만 유지
     *
     * @param userNo     사용자 번호
     * @param prdNo      상품 번호
     * @param prdSubCate 서브 카테고리 번호 (없으면 null)
     */
    void addRecentProduct(Long userNo, Long prdNo, Long prdSubCate);

    /**
     * 특정 사용자의 최근 본 상품 목록 조회
     * - 최근 본 시간 기준 내림차순
     * - 최대 10개
     *
     * @param userNo 사용자 번호
     * @return 최근 본 상품 목록
     */
    List<RecentProductResponse> getRecentProducts(Long userNo);

    /**
     * userId(loginId)로 userNo(PK) 조회
     *
     * @param userId 로그인 ID
     * @return 사용자 번호(PK)
     */
    Long getUserNoByUserId(String userId);
}