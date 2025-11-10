package com.routy.routyback.mapper.user;

import com.routy.routyback.dto.CartResponseDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CartMapper {
    // 4.1 장바구니 목록 조회
    List<CartResponseDTO.CartItemDTO> findItemByUserNo(Long userNo);

    // 4.2 상품 추가
    Long findCartItemIdByUserAndProduct(@Param("userNo") Long userNo,
                                        @Param("productId") Long productId);

    // 4.2 새 상품 Insert
    void insertItem(@Param("userNo") Long userNo,
                    @Param("productId") Long productId,
                    @Param("quantity") int quantity);

    // 4.2 수량 Update (상품 페이지에서 장바구니에 담기를 다시 누른 경우)
    void addStock(@Param("cartNo") Long cartNo,
                  @Param("quantity") int quantity);

    // 4.3 상품 속성 변경 - 수량 변경
    int updateQuantity(@Param("userNo") Long userNo,
                       @Param("cartItemId") Long cartItemId,
                       @Param("quantity") int quantity);

    // 4.3 선택 변경
    int updateSelected(@Param("userNo") Long userNo,
                       @Param("cartItemId") Long cartItemId,
                       @Param("selected") boolean selected);

    // 4.4 '전체' 상품 선택/해제
    int updateAllSelected(@Param("userNo") Long userNo,
                          @Param("selected") boolean selected);

    // 4.5 개별 상품 삭제
    int deleteItem(@Param("userNo") Long userNo,
                   @Param("cartItemId") Long cartItemId);

    // 4.6 선택된 상품 일괄 삭제 'cartSelect = 1' 조건
    int deleteSelectedItems(Long userNo);
}
