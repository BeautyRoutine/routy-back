package com.routy.routyback.mapper.user.mypage;

import com.routy.routyback.dto.user.mypage.UserLikeResponse;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 사용자 좋아요 관련 DB 접근을 담당하는 Mapper 인터페이스입니다.
 * USER_LIKE 테이블과 PRODUCT 정보를 조회/추가/삭제합니다.
 */
@Mapper
public interface UserLikeMapper {

    /**
     * 특정 사용자의 좋아요 상품 목록을 조회합니다.
     *
     * @param userId 사용자 번호
     * @param type 좋아요 타입(PRODUCT 또는 BRAND)
     * @return 좋아요 목록 리스트
     */
    List<UserLikeResponse> selectUserLikeProducts(@Param("userId") String userId,
        @Param("type") String type);

    /**
     * 사용자에게 좋아요를 추가합니다.
     *
     * @param userId 사용자 번호
     * @param productId 상품 또는 브랜드 번호
     * @param type 좋아요 타입(PRODUCT 또는 BRAND)
     * @return insert 성공 시 1, 실패 시 0
     */
    int insertLike(@Param("userId") String userId,
        @Param("productId") Long productId,
        @Param("type") String type);

    /**
     * 사용자 좋아요를 삭제합니다.
     *
     * @param userId 사용자 번호
     * @param productId 상품 번호
     * @param type 좋아요 타입(PRODUCT 또는 BRAND)
     * @return delete 성공 시 1, 실패 시 0
     */
    int deleteLike(@Param("userId") String userId,
        @Param("productId") Long productId,
        @Param("type") String type);

    /**
     * 특정 상품이 좋아요 되어있는지 확인합니다.
     *
     * @param userId 사용자 번호
     * @param productId 상품 번호
     * @param type 좋아요 타입(PRODUCT 또는 BRAND)
     * @return 존재하면 1 이상, 아니면 0
     */
    int countLike(@Param("userId") String userId,
        @Param("productId") Long productId,
        @Param("type") String type);
}