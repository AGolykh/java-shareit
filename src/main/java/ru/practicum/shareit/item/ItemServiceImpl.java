package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.BookingDtoModel;
import ru.practicum.shareit.booking.BookingMapper;
import ru.practicum.shareit.booking.BookingRepository;
import ru.practicum.shareit.exception.ObjectCreationException;
import ru.practicum.shareit.exception.ObjectNotFoundException;
import ru.practicum.shareit.exception.WrongOwnerException;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserService;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;
    private final BookingRepository bookingRepository;
    private final UserService userService;


    @Override
    public List<ItemDto> search(String text) {
        if (text.length() == 0) {
            log.debug("Search by empty text.");
            return Collections.emptyList();
        }
        List<ItemDto> result = itemRepository
                .search(text).stream()
                .map(ItemMapper::mapToDto)
                .collect(Collectors.toList());
        log.info("Found {} item(s).", result.size());
        return result;
    }

    @Override
    public List<ItemDto> getByUserId(Long userId) {
        List<ItemDto> result = itemRepository.findAllByOwner(userId).stream()
                .map(ItemMapper::mapToDto)
                .collect(Collectors.toList());
        log.info("Found {} item(s).", result.size());
        return result;
    }

    @Override
    public ItemDto getById(Long itemId) {
        ItemDto result = itemRepository
                .findById(itemId)
                .map(ItemMapper::mapToDto)
                .orElseThrow(() -> new ObjectNotFoundException("Item", itemId));
        log.info("User {} is found.", result.getId());

        List<BookingDtoModel> bookings = bookingRepository.findAllByItemIdOrderByStartDesc(result.getId())
                .stream()
                .map(BookingMapper::mapToDtoModel)
                .collect(Collectors.toList());

        return result;
    }

    @Override
    public ItemDto create(Long userId, ItemDto itemDto) {
        User user = userService.getUserById(userId);
        Item item = new Item();
        item.setOwner(user.getId());
        ItemDto result = Optional.of(itemRepository.save(ItemMapper.mapToItem(itemDto, item)))
                .map(ItemMapper::mapToDto)
                .orElseThrow(() -> new ObjectCreationException("Item", itemDto.getName()));
        log.info("Item {} {} created.", result.getId(), result.getName());
        return result;
    }

    @Override
    public ItemDto update(Long userId, Long itemId, ItemDto newItem) {
        User user = userService.getUserById(userId);
        Item oldItem = getItemById(itemId);
        if (!user.getId().equals(oldItem.getOwner())) {
            log.warn("User {} is not the owner of the item {}.", userId, newItem.getId());
            throw new WrongOwnerException(userId, "Item", newItem.getId());
        }
        ItemDto result = Optional.of(itemRepository.save(ItemMapper.mapToItem(newItem, oldItem)))
                .map(ItemMapper::mapToDto)
                .orElseThrow(() -> new ObjectCreationException("Item", oldItem.getName()));
        log.info("Item {} {} updated.", result.getId(), result.getName());
        return result;
    }

    public Item getItemById(Long itemId) {
        return itemRepository
                .findById(itemId)
                .orElseThrow(() -> new ObjectNotFoundException("Item", itemId));
    }
}