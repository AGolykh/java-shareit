package ru.practicum.shareit.unit;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.practicum.shareit.item.ItemRepository;
import ru.practicum.shareit.request.ItemRequestRepository;
import ru.practicum.shareit.request.ItemRequestServiceImpl;
import ru.practicum.shareit.user.UserServiceImpl;

@ExtendWith(MockitoExtension.class)
class ItemRequestServiceImplTest {

    @Mock
    private ItemRequestRepository itemRequestRepository;
    @Mock
    private ItemRepository itemRepository;

    @InjectMocks
    private ItemRequestServiceImpl itemRequestService;
    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void getByRequesterId() {
    }

    @Test
    void getAll() {
    }

    @Test
    void create() {
    }

    @Test
    void getById() {
    }

    @Test
    void getRequestById() {
    }
}