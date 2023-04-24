package ru.practicum.shareit.item.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.IncorrectObjectIdException;
import ru.practicum.shareit.exception.IsNotCreatedException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemMapper;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.UserRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;

    @Override
    public ItemDto create(Long userId, ItemDto itemDto) {
        if (userRepository.findById(userId).isEmpty()) {
            log.warn("User {} is not found.", userId);
            throw new IncorrectObjectIdException(String.format("User %d is not found.", userId));
        }
        Item item = new Item();
        item.setOwner(userId);
        Optional<Item> result = itemRepository.create(ItemMapper.mapToItem(itemDto, item));
        if (result.isEmpty()) {
            log.warn("Item {} is not created.",
                    itemDto.getName());
            throw new IsNotCreatedException(String.format("Item %s is not created.",
                    itemDto.getName()));
        }
        return ItemMapper.mapToDto(result.get());
    }

    @Override
    public ItemDto update(Long userId, Long itemId, ItemDto newItem) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            log.warn("User {} is not found.", userId);
            throw new IncorrectObjectIdException(String.format("User %d is not found.", userId));
        }

        Optional<Item> oldItem = itemRepository.findById(itemId);
        if (oldItem.isEmpty()) {
            log.warn("Item {} is not found.", itemId);
            throw new IncorrectObjectIdException(String.format("Item %d is not found.", itemId));
        }


        if (!user.get().getId().equals(oldItem.get().getOwner())) {
            log.warn("User {} is not the owner of the  item {}.", userId, newItem.getId());
            throw new IncorrectObjectIdException(String.format("User %d is not the owner of the item %d.",
                    userId, newItem.getId()));
        }

        Optional<Item> updatedItem = itemRepository.update(ItemMapper.mapToItem(newItem, oldItem.get()));
        if (updatedItem.isEmpty()) {
            log.warn("Item {} is not updated.", itemId);
            throw new IncorrectObjectIdException(String.format("Item %d is not updated.", itemId));
        }

        log.info("Item {} {} updated.", updatedItem.get().getId(), updatedItem.get().getName());
        return ItemMapper.mapToDto(updatedItem.get());
    }

    @Override
    public List<ItemDto> search(String text) {
        if (text.length() == 0) {
            log.debug("Search by empty text.");
            return Collections.emptyList();
        }
        List<ItemDto> result = itemRepository.search(text).stream()
                .map(ItemMapper::mapToDto)
                .collect(Collectors.toList());
        log.info("Found {} item(s).", result.size());
        return result;
    }

    @Override
    public ItemDto getById(Long itemId) {
        Optional<Item> result = itemRepository.findById(itemId);
        if (result.isEmpty()) {
            log.warn("Item {} is not found.", itemId);
            throw new IncorrectObjectIdException(String.format("User %d is not found.", itemId));
        }
        log.info("Item {} is found.", result.get().getId());
        return ItemMapper.mapToDto(result.get());
    }

    @Override
    public List<ItemDto> getByUserId(Long userId) {
        List<ItemDto> result = itemRepository.findByUserId(userId).stream()
                .map(ItemMapper::mapToDto)
                .collect(Collectors.toList());
        log.info("Found {} item(s).", result.size());
        return result;
    }
}