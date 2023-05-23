package ru.practicum.shareit.item;

import ru.practicum.shareit.comment.dto.CommentDto;
import ru.practicum.shareit.comment.dto.CommentInputDto;
import ru.practicum.shareit.item.dto.*;

import java.util.List;

public interface ItemService {

    List<ItemFullDto> search(String text);

    List<ItemFullDto> getByUserId(Long userId);

    ItemFullDto getById(Long userId, Long itemId);

    ItemFullDto create(Long userId, ItemInputDto itemInputDto);

    ItemFullDto update(Long userId, Long itemId, ItemInputDto itemInputDto);

    Item getItemById(Long itemId);

    CommentDto addComment(Long userId, Long itemId, CommentInputDto commentInputDto);
}