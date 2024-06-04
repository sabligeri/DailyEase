package com.codecool.men.controller.dto.shoppinglist;

public record ShoppingListItemDTO(
        Long itemId,
        String name,
        int quantity,
        boolean isBought
) {
}
