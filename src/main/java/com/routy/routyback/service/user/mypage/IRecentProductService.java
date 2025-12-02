package com.routy.routyback.service.user.mypage;

import com.routy.routyback.dto.user.mypage.RecentProductResponse;
import java.util.List;

/**
 * 최근 본 상품 서비스 인터페이스
 */
public interface IRecentProductService {

    List<RecentProductResponse> getRecentProducts(Long userNo); // 최근 본 상품 목록
}