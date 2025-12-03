package com.routy.routyback.service.user.mypage;

import com.routy.routyback.dto.user.RecentProductResponse;
import java.util.List;

/**
 * 최근 본 상품 서비스 인터페이스
 */
public interface IRecentProductService {

    List<RecentProductResponse> getRecentProducts(String userId); // 최근 본 상품 목록
}