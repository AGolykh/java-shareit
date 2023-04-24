package ru.practicum.shareit.item.service;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.user.service.UserService;
import ru.practicum.shareit.user.dto.UserDto;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
@SpringBootTest
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ItemServiceImplTest {

    private final UserService userService;
    private final ItemService itemService;

    @Test
    void create_return1Item_added5Items() {
        addData();
        assertThat(itemService.getById(2L))
                .hasFieldOrPropertyWithValue("name", "Кирпич")
                .hasFieldOrPropertyWithValue("description", "При помощи веревки может стать оружием масового поражения");
    }

    @Test
    void update_returnUpdatedItem_add5Items() {
        addData();
        assertThat(itemService.getById(2L))
                .hasFieldOrPropertyWithValue("name", "Кирпич")
                .hasFieldOrPropertyWithValue("description", "При помощи веревки может стать оружием масового поражения");
        itemService.update(2L, 2L, new ItemDto("Щебень", "Не пережил прошлую аренду", true));
        assertThat(itemService.getById(2L))
                .hasFieldOrPropertyWithValue("name", "Щебень")
                .hasFieldOrPropertyWithValue("description", "Не пережил прошлую аренду");
    }

    @Test
    void getById_returnItemWith3Id_added5Items() {
        addData();
        assertThat(itemService.getById(3L))
                .hasFieldOrPropertyWithValue("name", "Барбос")
                .hasFieldOrPropertyWithValue("description", "Для защиты от людей, грызунов и крупного рогатого скота, требуется палка");
    }

    @Test
    void getByUserId_return2Items_added5Items() {
        addData();
        assertThat(itemService.getByUserId(2L).size()).isEqualTo(2);
    }

    @Test
    void search_returnItemByText_added5Items() {
        addData();
        assertThat(itemService.search("палка").size()).isEqualTo(2);
    }

    void addData() {
        userService.create(new UserDto("Viktor", "Vik@yandex.ru"));
        userService.create(new UserDto("Mirko", "crocop@yandex.ru"));
        userService.create(new UserDto("Alex", "emel@yandex.ru"));

        itemService.create(1L, new ItemDto("Вставной глаз", "Для охмурения мамзелей", true));
        itemService.create(2L, new ItemDto("Кирпич", "При помощи веревки может стать оружием масового поражения", true));
        itemService.create(3L, new ItemDto("Барбос", "Для защиты от людей, грызунов и крупного рогатого скота, требуется палка", true));
        itemService.create(2L, new ItemDto("Палка", "Колупалка", true));
        itemService.create(3L, new ItemDto("Ведро", "Не ведро", true));
    }
}
