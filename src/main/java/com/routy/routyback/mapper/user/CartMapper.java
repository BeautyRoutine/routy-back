package com.routy.routyback.mapper.user;

import com.routy.routyback.dto.CartResponseDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CartMapper {
    // 장바구니 목록 조회
    List<CartResponseDTO.CartItemDTO> findItemsByUserNo(Long userNo);

    Long findCartItemIdByUserAndProduct(@Param("userNo") Long userNo,
                                        @Param("productId") Long productId);

    // 새 항목 추가 (MERGE문에서 사용)
    void insertNewItem(@Param("userNo") Long userNo,
                       @Param("productId") Long productId,
                       @Param("quantity") int quantity);

    // 상품 속성 변경 - 수량 변경
    int updateQuantity(@Param("userNo") Long userNo,
                       @Param("cartItemId") Long cartItemId,
                       @Param("quantity") int quantity);

    // 선택여부 변경
    int updateSelected(@Param("userNo") Long userNo,
                       @Param("cartItemId") Long cartItemId,
                       @Param("selected") boolean selected);

    // '전체' 상품 선택/해제
    int updateAllSelected(@Param("userNo") Long userNo,
                          @Param("selected") boolean selected);

    // 상품 삭제
    int deleteItems(@Param("userNo") Long userNo,
                    @Param("cartItemIds") List<Long> cartItemIds);

}
