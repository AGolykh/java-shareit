package ru.practicum.shareit.item;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findAllByOwner(Long ownerId);

    List<Item> findAllByNameOrDescriptionContainsIgnoreCaseAndAvailableTrue(String name, String description);
}