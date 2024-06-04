package com.codecool.men.controller;

import com.codecool.men.controller.dto.shoppinglist.NewItemDTO;
import com.codecool.men.controller.dto.shoppinglist.ShoppingListItemDTO;
import com.codecool.men.repository.model.ShoppingListItem;
import com.codecool.men.service.ShoppingListItemService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
// userId nems zükséges mivel a jwt token-ből ki lehet szedni az id-t
@RestController
@RequestMapping("/api/shopping/{userId}")
public class ItemController {
    private final ShoppingListItemService listItemService;

    public ItemController(ShoppingListItemService listItemService) {
        this.listItemService = listItemService;
    }

    @GetMapping
    public List<ShoppingListItemDTO> getAllItems(@PathVariable long userId) {
        return listItemService.getAllItems(userId);
    }

    @PostMapping
    public boolean addItem(@RequestBody NewItemDTO newItemDTO, @PathVariable long userId){
        return listItemService.addItem(newItemDTO, userId);
    }
    @PutMapping("/quantity/{itemId}")
    public boolean updateItemQuantity(@PathVariable("itemId") Long itemId, @RequestParam int quantity, @PathVariable long userId) {
        return listItemService.updateItemQuantity(itemId, quantity);
    }
    @PutMapping("/bought/{itemId}")
    public boolean updateBought(@PathVariable("itemId") Long itemId, @RequestParam boolean isBought, @PathVariable long userId) {
        return listItemService.updateBought(itemId, isBought);
    }

    @DeleteMapping("/{itemId}")
    public boolean deleteNote( @PathVariable("itemId") Long itemId, @PathVariable long userId) {
        return listItemService.deleteItem(itemId, userId);
    }

}
