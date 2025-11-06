package com.routy.routyback.dto;

import lombok.Builder;
import lombok.Getter;
import java.util.List;

@Getter
@Builder
public class CartViewResponseDTO {

    private SummaryDTO summary;
    private List<CartItemDTO> items;

    @Getter
    @Builder
    public static class SummaryDTO {
        private long totalProductAmount;
        private int deliveryFee;
        private long finalPaymentAmount;
    }

    @Getter
    @Builder
    public static class CartItemDTO {
        private long cartItemId;
        private long productId;
        private String name;
        private String brand;
        private int price;
        private int quantity;
        private String imageUrl;
        private boolean selected;
        private SkinAlertDTO skinAlert;
    }

    @Getter
    @Builder
    public static class SkinAlertDTO {
        private String type;
        private String message;
    }

}
