package com.routy.routyback.service.user;

import com.routy.routyback.dto.CartResponseDTO;
import com.routy.routyback.mapper.user.CartMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartMapper cartMapper;

    @Transactional(readOnly = true)
    public CartResponseDTO getCartView(Long userNo) {

        List<CartResponseDTO.CartItemDTO> items = cartMapper.findItemByUserNo(userNo);

        long totalProductAmount = 0;
        for(CartResponseDTO.CartItemDTO item : items){
            if(item.isSelected()){
                totalProductAmount += (long) item.getPrice() * item.getQuantity();
            }
        }

        int deliveryFee = (totalProductAmount >= 30000 || items.isEmpty()) ? 0 : 3000;
        long finalPaymentAmount = totalProductAmount + deliveryFee;

        CartResponseDTO.SummaryDTO summary = CartResponseDTO.SummaryDTO.builder()
                .totalProductAmount(totalProductAmount)
                .deliveryFee(deliveryFee)
                .finalPaymentAmount(finalPaymentAmount)
                .build();

        return CartResponseDTO.builder()
                .summary(summary)
                .items(items)
                .build();
    }

    @Transactional
    public CartResponseDTO addItem(Long userNo, Long productId, int quantity) {
        Long existingCartItemId = cartMapper.findCartItemIdByUserAndProduct(userNo, productId);

        if(existingCartItemId != null){
            cartMapper.addStock(existingCartItemId, quantity);
        } else{
            cartMapper.insertItem(userNo, productId, quantity);
        }

        return getCartView(userNo);
    }

    @Transactional
    public CartResponseDTO updateItem(Long userNo, long cartItemId, Integer quantity, Boolean selected){
        if(quantity != null){
            if(quantity <= 0){
                cartMapper.deleteItem(userNo, cartItemId);
            } else{
                cartMapper.updateQuantity(userNo, cartItemId, quantity);
            }
        }

            if(selected != null){
                cartMapper.updateSelected(userNo, cartItemId, selected);
            }

            return getCartView(userNo);
    }

    @Transactional
    public CartResponseDTO updateAllItem(Long userNo, Boolean selected) {

        cartMapper.updateAllSelected(userNo, selected);

        return getCartView(userNo);
    }

    @Transactional
    public CartResponseDTO deleteItem(Long userNo, Long cartItemId){

        cartMapper.deleteItem(userNo, cartItemId);

        return getCartView(userNo);
    }

    @Transactional
    public CartResponseDTO deleteSelectedItem(Long userNo){

        cartMapper.deleteSelectedItems(userNo);

        return getCartView(userNo);
    }
}
