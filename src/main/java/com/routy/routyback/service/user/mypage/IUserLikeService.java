package com.routy.routyback.service.user.mypage;

import com.routy.routyback.dto.user.UserLikeResponse;

import java.util.List;

/**
 * 사용자 좋아요 관련 비즈니스 로직을 정의하는 인터페이스입니다.
 * 컨트롤러에서 호출되며 DB 처리는 Mapper에서 담당합니다.
 */
public interface IUserLikeService {

    /**
     * 특정 사용자의 좋아요 상품 또는 브랜드 목록을 조회합니다.
     *
     * @param userNo 사용자 번호
     * @param type 좋아요 타입(PRODUCT 또는 BRAND)
     * @return 좋아요 리스트
     */
    List<UserLikeResponse> getUserLikeProducts(Long userNo, String type);

    /**
     * 좋아요를 추가합니다.
     *
     * @param userNo 사용자 번호
     * @param productId 상품 또는 브랜드 ID
     * @param type 좋아요 타입(PRODUCT 또는 BRAND)
     * @return 성공 시 1, 실패 시 0
     */
    int addLike(Long userNo, Long productId, String type);

    /**
     * 좋아요를 삭제합니다.
     *
     * @param userNo 사용자 번호
     * @param productId 상품 또는 브랜드 번호
     * @param type 좋아요 타입
     * @return 성공 시 1, 실패 시 0
     */
    int removeLike(Long userNo, Long productId, String type);

    /**
     * 특정 상품이 좋아요 되어 있는지 확인합니다.
     *
     * @param userNo 사용자 번호
     * @param productId 상품 또는 브랜드 번호
     * @param type 좋아요 타입
     * @return 좋아요가 존재하면 true
     */
    boolean isLiked(Long userNo, Long productId, String type);
}