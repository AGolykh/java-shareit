package ru.practicum.shareit.item;

import ru.practicum.shareit.item.dto.ItemBookingDto;
import ru.practicum.shareit.item.dto.ItemDto;

import java.util.List;

public interface ItemService {

    List<ItemDto> search(String text);

    List<ItemBookingDto> getByUserId(Long userId);

    ItemBookingDto getById(Long userId, Long itemId);

    ItemDto create(Long userId, ItemDto itemDto);

    ItemDto update(Long userId, Long itemId, ItemDto itemDto);

    Item getItemById(Long itemId);
}