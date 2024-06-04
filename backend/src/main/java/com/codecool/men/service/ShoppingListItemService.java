package com.codecool.men.service;

import com.codecool.men.controller.dto.shoppinglist.NewItemDTO;
import com.codecool.men.controller.dto.shoppinglist.ShoppingListItemDTO;
import com.codecool.men.controller.exceptions.OperationFailedException;
import com.codecool.men.controller.exceptions.UserNotFoundException;
import com.codecool.men.repository.ShoppingListRepository;
import com.codecool.men.repository.UserRepository;
import com.codecool.men.repository.model.Note;
import com.codecool.men.repository.model.ShoppingListItem;
import com.codecool.men.repository.model.UserEntity;
import org.apache.catalina.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ShoppingListItemService {
    private final ShoppingListRepository shoppingListRepository;
    private final UserRepository userRepository;

    public ShoppingListItemService(ShoppingListRepository shoppingListRepository, UserRepository userRepository) {
        this.shoppingListRepository = shoppingListRepository;
      this.userRepository = userRepository;
    }

    public List<ShoppingListItemDTO> getAllItems(long userId){
        List<ShoppingListItem> shoppingListItems = shoppingListRepository.findByUserEntityId(userId);
        return shoppingListItems.stream().map(item -> new ShoppingListItemDTO(item.getId(), item.getProductName(), item.getQuantity(), item.isBought())).toList();
    }

    public boolean addItem(NewItemDTO newItemDTO, long userId){
        UserEntity user = getUser(userId);
        ShoppingListItem shoppingListItem = new ShoppingListItem();
        createItem(newItemDTO, shoppingListItem, user);
        user.addShoppingListItems(shoppingListItem);
        return true;
    }

    private void createItem(NewItemDTO newItemDTO, ShoppingListItem shoppingListItem, UserEntity user){
        shoppingListItem.setProductName(newItemDTO.name());
        shoppingListItem.setBought(false);
        shoppingListItem.setUserEntity(user);
        shoppingListRepository.save(shoppingListItem);
    }

    public boolean updateItemQuantity(Long itemId, int quantity) {
        Optional<ShoppingListItem> optionalItem = shoppingListRepository.findById(itemId);
        if (optionalItem.isPresent()) {
            ShoppingListItem item = optionalItem.get();
            item.setQuantity(quantity);
            shoppingListRepository.save(item);
            return true;
        }
        throw new OperationFailedException("Unable to update the quantity of the item!"); // Item with given ID not found
    }

    public boolean updateBought(Long itemId, boolean isBought){
        Optional<ShoppingListItem> listItemOptional = shoppingListRepository.findById(itemId);
        if (listItemOptional.isPresent()) {
            ShoppingListItem item = listItemOptional.get();
            item.setBought(isBought);
            shoppingListRepository.save(item);
            return true;
        }
        return false;
    }

    public boolean deleteItem(long itemId, long userId) {
        UserEntity user = getUser(userId);
        ShoppingListItem shoppingListItem = shoppingListRepository.findById(itemId).orElse(null);
        if (shoppingListItem != null) {
            user.removeShoppingListItems(shoppingListItem);
            shoppingListRepository.delete(shoppingListItem);
            return true;
        }
        throw new OperationFailedException("Note not found!");
    }
    private UserEntity getUser(long id) {
        return userRepository
                .findById(id)
                .orElseThrow(UserNotFoundException::new);
    }

}
