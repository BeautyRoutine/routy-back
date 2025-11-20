package com.routy.routyback.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Builder
public class CartResponseDTO {

    private SummaryDTO summary;
    private List<CartItemDTO> items;

    @Getter
    @Builder
    public static class SummaryDTO {

        private int deliveryFee;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CartItemDTO {

        private long cartItemId;
        private long productId;
        private String name;
        private String brand;
        private int price;
        private int quantity;
        private String imageUrl;
        private String allergenList;
        private String dangerList;

        @Setter
        private SkinAlertDTO skinAlert;
    }

    @Getter
    @Builder
    public static class SkinAlertDTO {

        private String type;
        private String message;
    }

}
