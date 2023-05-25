package ru.practicum.shareit.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import ru.practicum.shareit.item.Item;
import ru.practicum.shareit.item.ItemRepository;
import ru.practicum.shareit.request.ItemRequest;
import ru.practicum.shareit.request.ItemRequestRepository;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserRepository;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@AutoConfigureTestDatabase
class ItemRequestRepositoryTest {

    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ItemRequestRepository itemRequestRepository;

    User userFromDb;
    Item itemFromDb;
    ItemRequest itemRequestFromDb;

    Pageable pageable;

    @BeforeEach
    public void beforeEach() {
        User user = new User();
        user.setName("vityok");
        user.setEmail("vityok@mail.com");
        userFromDb = userRepository.save(user);

        ItemRequest itemRequest = new ItemRequest();
        itemRequest.setDescription("Нужен кирпич");
        itemRequest.setRequester(user);
        itemRequest.setCreated(LocalDateTime.now().minusDays(1).truncatedTo(ChronoUnit.SECONDS));
        itemRequestFromDb = itemRequestRepository.save(itemRequest);

        Item item = new Item();
        item.setName("Кирпич");
        item.setDescription("Шлакоблокунь");
        item.setAvailable(true);
        item.setOwner(userFromDb);
        item.setItemRequest(itemRequestFromDb);
        itemFromDb = itemRepository.save(item);

        pageable = PageRequest.of(1/20, 20, Sort.by("id").descending());
    }

    @Test
    void findAllByRequesterId_return1ItemRequest_added1Request() {
        assertEquals(List.of(itemRequestFromDb),
                itemRequestRepository.findAllByRequesterId(userFromDb.getId()));
    }

    @Test
    void findAllByRequesterId_returnEmpty_added1Request() {
        assertEquals(List.of(), itemRequestRepository.findAllByRequesterId(3L));
    }

    @Test
    void findAllByRequesterIdNot_return1ItemRequest_added1Request() {
        assertEquals(List.of(itemRequestFromDb),
                itemRequestRepository.findAllByRequesterIdNot(9999L, pageable));
    }

    @Test
    void findAllByRequesterIdNot_returnEmpty_added1Request() {
        assertEquals(List.of(),
                itemRequestRepository.findAllByRequesterIdNot(userFromDb.getId(), pageable));
    }
}