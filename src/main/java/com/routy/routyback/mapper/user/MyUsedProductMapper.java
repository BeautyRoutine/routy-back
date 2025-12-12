package com.routy.routyback.mapper.user;              // Mapper 패키지 경로

import org.apache.ibatis.annotations.Mapper;          // MyBatis 매퍼 어노테이션
import org.apache.ibatis.annotations.Param;           // 파라미터 명시용 어노테이션
import java.util.List;                                // List 타입 사용

/**
 * MyUsedProductMapper
 *
 * - MY_USED_PRODUCT 테이블과 연결되는 MyBatis Mapper 인터페이스
 * - 사용자가 '현재 사용 중'으로 체크한 제품(PRDNO)을 조회/추가/삭제한다.
 */
@Mapper                                              // MyBatis가 인식할 수 있는 Mapper로 등록
public interface MyUsedProductMapper {

    /**
     * 사용자가 현재 사용 중인 제품 번호(PRDNO) 리스트 조회
     *
     * @param userNo  USERS 테이블의 PK (Long)
     * @return        사용 중인 제품 번호(PRDNO) 목록
     */
    List<Long> selectUsedProducts(@Param("userNo") Long userNo);

    /**
     * 사용 중으로 제품 추가 (USERNO + PRDNO 저장)
     *
     * @param userNo  사용자 PK
     * @param prdNo   제품 번호(PRDNO)
     * @return        INSERT된 row 수
     */
    int insertUsedProduct(@Param("userNo") Long userNo, @Param("prdNo") Long prdNo);

    /**
     * 사용 중 해제 (USERNO + PRDNO 삭제)
     *
     * @param userNo  사용자 PK
     * @param prdNo   제품 번호(PRDNO)
     * @return        DELETE된 row 수
     */
    int deleteUsedProduct(@Param("userNo") Long userNo, @Param("prdNo") Long prdNo);
}
