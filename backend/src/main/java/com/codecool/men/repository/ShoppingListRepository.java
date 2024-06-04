package com.codecool.men.repository;

import com.codecool.men.repository.model.ShoppingListItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ShoppingListRepository extends JpaRepository<ShoppingListItem, Long> {
    Optional<ShoppingListItem> findById(Long id);

    List<ShoppingListItem> findByUserEntityId(long id);
}
