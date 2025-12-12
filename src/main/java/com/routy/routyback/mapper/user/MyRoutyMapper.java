package com.routy.routyback.mapper.user;                // MyRouty 관련 Mapper 패키지

import com.routy.routyback.dto.user.myrouty.MyProductResponse; // 내 제품 조회 DTO
import org.apache.ibatis.annotations.Mapper;            // MyBatis Mapper 어노테이션
import org.apache.ibatis.annotations.Param;             // SQL 파라미터 명시용 어노테이션
import java.util.List;                                  // List 타입 사용

/**
 * MyRoutyMapper
 *
 * - 사용자가 구매한 제품 목록을 조회하는 Mapper 인터페이스
 * - userId → userNo 변환은 Service 층에서 처리하므로,
 *   여기서는 오직 userNo(Long) 기반 조회만 수행한다.
 */
@Mapper                                                 // MyBatis 매퍼 등록
public interface MyRoutyMapper {

    /**
     * 사용자가 구매한 제품 목록 조회
     *
     * @param userNo USERS.userNo(PK)
     * @return 구매한 제품 정보 목록(MyProductResponse 리스트)
     */
    List<MyProductResponse> selectMyProducts(
        @Param("userNo") Long userNo                // 구매 제품을 조회할 사용자 PK
    );
}