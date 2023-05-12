package ru.practicum.shareit.user.service;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import ru.practicum.shareit.user.UserService;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class UserServiceImplTest {

    private final UserService userService;

    @Test
    void getById_returnUserWith3Id_added3User() {
        addData();
        assertThat(userService.getById(3L))
                .hasFieldOrPropertyWithValue("name", "Alex")
                .hasFieldOrPropertyWithValue("email", "emel@yandex.ru");
    }

    @Test
    void create_returnUserWith2Id_added3User() {
        addData();
        assertThat(userService.getById(2L))
                .hasFieldOrPropertyWithValue("name", "Mirko")
                .hasFieldOrPropertyWithValue("email", "crocop@yandex.ru");
    }

    @Test
    void getAll_returnAllUsers_added3Users() {
        addData();
        assertThat(userService.getAll().size()).isEqualTo(3);
    }

    @Test
    void update_returnUpdatedUser_added3Users() {
        addData();
        assertThat(userService.getById(2L))
                .hasFieldOrPropertyWithValue("name", "Mirko")
                .hasFieldOrPropertyWithValue("email", "crocop@yandex.ru");
        userService.update(new UserDto("McLaren", "zjigul@yandex.ru"), 2L);
        assertThat(userService.getById(2L))
                .hasFieldOrPropertyWithValue("name", "McLaren")
                .hasFieldOrPropertyWithValue("email", "zjigul@yandex.ru");
    }

    @Test
    void delete_return2Users_added3Users() {
        addData();
        assertThat(userService.getAll().size()).isEqualTo(3);
        userService.deleteById(3L);
        assertThat(userService.getAll().size()).isEqualTo(2);
    }

    void addData() {
        userService.create(new UserDto("Viktor", "Vik@yandex.ru"));
        userService.create(new UserDto("Mirko", "crocop@yandex.ru"));
        userService.create(new UserDto("Alex", "emel@yandex.ru"));
    }
}