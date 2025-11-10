package com.routy.routyback.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class CartRequestDTO {

    @Getter
    @Setter
    public static class AddItem{
        private Long productId;
        private int quantity;
    }

    @Getter
    @Setter
    public static class UpdateItem{
        private Integer quantity;
        private Boolean selected;
    }

    @Getter
    @Setter
    public static class UpdateAllSelected{
        private Boolean selected;
    }

    @Getter
    @Setter
    public static class DeleteItems {
        private List<Long> cartItemIds;
    }
}
