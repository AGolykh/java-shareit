package ru.practicum.shareit.item.dto;

import ru.practicum.shareit.exception.ObjectCreationException;
import ru.practicum.shareit.item.Item;
import ru.practicum.shareit.user.dto.UserMapper;

public class ItemMapper {

    public static ItemFullDto mapToFullDto(Item item) {
        return new ItemFullDto(item.getId(),
                item.getName(),
                item.getDescription(),
                item.isAvailable(),
                UserMapper.mapToShortDto(item.getOwner()));
    }

    public static ItemShortDto mapToShortDto(Item item) {
        return new ItemShortDto(item.getId(),
                item.getName(),
                item.getDescription(),
                item.isAvailable());
    }

    public static Item mapToNewItem(ItemInputDto itemInputDto, Item item) {
        item.setName(itemInputDto.getName());
        item.setDescription(itemInputDto.getDescription());
        item.setAvailable(itemInputDto.getAvailable());
        return item;
    }

    public static Item mapToUpdateItem(ItemUpdateDto itemUpdateDto, Item item) {

        if (itemUpdateDto.getName() == null
                && itemUpdateDto.getDescription() == null
                && itemUpdateDto.getAvailable() == null) {
            throw new ObjectCreationException("Item", item.getName());
        }

        if (itemUpdateDto.getName() != null) {
            item.setName(itemUpdateDto.getName());
        }

        if (itemUpdateDto.getDescription() != null) {
            item.setDescription(itemUpdateDto.getDescription());
        }

        if (itemUpdateDto.getAvailable() != null) {
            item.setAvailable(itemUpdateDto.getAvailable());
        }

        return item;
    }

}
