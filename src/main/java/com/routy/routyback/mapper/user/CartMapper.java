package com.routy.routyback.mapper.user;

import com.routy.routyback.dto.CartResponseDTO;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface CartMapper {

    // 장바구니 목록 조회
    List<CartResponseDTO.CartItemDTO> findItemsByUserNo(Long userNo);

    // 새 항목 추가 (MERGE문에서 사용)
    void insertNewItem(@Param("userNo") Long userNo,
        @Param("productId") Long productId,
        @Param("quantity") int quantity);

    // 상품 속성 변경 - 수량 변경
    int updateQuantity(@Param("userNo") Long userNo,
        @Param("cartItemId") Long cartItemId,
        @Param("quantity") int quantity);

    // 상품 삭제
    int deleteItems(@Param("userNo") Long userNo,
        @Param("cartItemIds") List<Long> cartItemIds);

    // 장바구니 전체 상품 개수 조회 (헤더 숫자 표시용)
    // userNo가 가진 CART 레코드 개수만 반환
    int countCartItems(Long userNo);
}
