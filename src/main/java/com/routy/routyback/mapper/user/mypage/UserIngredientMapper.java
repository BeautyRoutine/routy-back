package com.routy.routyback.mapper.user.mypage;

import com.routy.routyback.dto.user.mypage.IngredientPreferenceResponse;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 사용자 선호/기피 성분 관련 Mapper
 * - USR_ING_MAPPING 테이블과 매핑됨
 * - 선호(1), 기피(2) 유형별로 조회/추가/삭제 기능 제공
 */
@Mapper
public interface UserIngredientMapper {

    /**
     * 사용자 선호 성분 목록 조회 (UIMAPTYPE = 1)
     *
     * @param userId 사용자 번호
     * @return 사용자가 등록한 선호 성분 리스트
     */
    List<IngredientPreferenceResponse> selectFocusIngredients(
        @Param("userId") String userId
    );

    /**
     * 사용자 기피 성분 목록 조회 (UIMAPTYPE = 2)
     *
     * @param userId 사용자 번호
     * @return 사용자가 등록한 기피 성분 리스트
     */
    List<IngredientPreferenceResponse> selectAvoidIngredients(
        @Param("userId") String userId
    );

    /**
     * 사용자 성분 추가 (선호/기피 공통)
     *
     * @param userId 사용자 번호
     * @param ingredientId 성분 번호 (INGNO)
     * @param type 성분 구분 (1 = 선호, 2 = 기피)
     * @return insert 적용 row 개수
     */
    int insertIngredient(
        @Param("userId") String userId,
        @Param("ingredientId") Long ingredientId,
        @Param("type") int type
    );

    /**
     * 사용자 성분 삭제 (선호/기피 공통)
     *
     * @param userId 사용자 번호
     * @param ingredientId 성분 번호
     * @param type 성분 구분 (1 = 선호, 2 = 기피)
     * @return delete 적용 row 개수
     */
    int deleteIngredient(
        @Param("userId") String userId,
        @Param("ingredientId") Long ingredientId,
        @Param("type") int type
    );

    /**
     * 사용자 성분 중복 여부 체크
     * 이미 등록된 성분인지 확인하는 용도
     *
     * @param userId 사용자 번호
     * @param ingredientId 성분 번호
     * @param type 성분 구분
     * @return 중복 개수 (0이면 등록 가능)
     */
    int countIngredient(
        @Param("userId") String userId,
        @Param("ingredientId") Long ingredientId,
        @Param("type") int type
    );
}