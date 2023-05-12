package ru.practicum.shareit.item.dto;

import ru.practicum.shareit.item.Item;

public class ItemMapper {

    public static ItemDto mapToItemDto(Item item) {
        return new ItemDto(item.getId(),
                item.getName(),
                item.getDescription(),
                item.isAvailable(),
                item.getOwner());
    }

    public static ItemBookingDto mapToItemBookingDto(Item item) {
        return new ItemBookingDto(item.getId(),
                item.getName(),
                item.getDescription(),
                item.isAvailable(),
                item.getOwner());
    }

    public static Item mapToItem(ItemDto itemDto, Item item) {
        if (itemDto.getName() != null) {
            item.setName(itemDto.getName());
        }

        if (itemDto.getDescription() != null) {
            item.setDescription(itemDto.getDescription());
        }

        if (itemDto.getAvailable() != null) {
            item.setAvailable(itemDto.getAvailable());
        }
        return item;
    }
}
