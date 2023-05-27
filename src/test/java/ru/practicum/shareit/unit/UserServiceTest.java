package ru.practicum.shareit.unit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserRepository;
import ru.practicum.shareit.user.UserServiceImpl;
import ru.practicum.shareit.user.dto.UserFullDto;
import ru.practicum.shareit.user.dto.UserInputDto;
import ru.practicum.shareit.user.dto.UserMapper;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    User user1;
    User user2;
    UserFullDto userFullDto1;
    UserFullDto userFullDto2;
    UserInputDto userInputDto1;
    UserInputDto userInputDto2;

    @BeforeEach
    void beforeEach() {
        user1 = new User(1L, "sadsa", "dsads@dsads.ru");
        userFullDto1 = UserMapper.mapToFullDto(user1);
        userInputDto1 = new UserInputDto(user1.getId(), user1.getName(), user1.getEmail());

        user2 = new User(2L, "sadsadsa", "dsasdadsds@ddsadssads.ru");
        userFullDto2 = UserMapper.mapToFullDto(user2);
        userInputDto2 = new UserInputDto(user2.getId(), user2.getName(), user2.getEmail());
    }

    @Test
    void getAll_return2Users_add2Users() {
        when(userRepository.findAll()).thenReturn(List.of(user1, user2));

        assertThat(userService.getAll().size()).isEqualTo(2);
        assertThat(userService.getAll()).asList().contains(userFullDto1, userFullDto2);
    }

    @Test
    void getById_returnUsers1_add2Users() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user1));

        assertThat(userService.getById(1L)).isEqualTo(userFullDto1);
    }

    @Test
    void getById_throwNullPointerException_add2Users() {
        assertThrows(NullPointerException.class, () -> userService.getById(999L));
    }

    @Test
    void create_returnUsers_addUsers() {
        when(userRepository.save(user1)).thenReturn(user1);

        assertThat(userService.create(userInputDto1)).isEqualTo(userFullDto1);
    }

    @Test
    void update_returnUsers_updateUsers() {
        when(userRepository.save(user1)).thenReturn(user1);
        when(userRepository.findById(user1.getId())).thenReturn(Optional.of(user1));

        assertThat(userService.update(userInputDto1, user1.getId())).isEqualTo(userFullDto1);
    }

    @Test
    void deleteById_void_addUsers() {
        when(userRepository.findById(user1.getId())).thenReturn(Optional.of(user1));
        userService.deleteById(user1.getId());
        verify(userRepository).deleteById(user1.getId());
    }

    @Test
    void deleteById_throwNullPointerException_emptyUsers() {
        assertThrows(NullPointerException.class, () -> userService.deleteById(999L));
    }

    @Test
    void getUserById_returnUsers_addUsers() {
        when(userRepository.findById(user1.getId())).thenReturn(Optional.of(user1));

        assertThat(userService.getUserById(user1.getId())).isEqualTo(user1);
    }

    @Test
    void getUserById_throwNullPointerException_emptyUsers() {
        assertThrows(NullPointerException.class, () -> userService.getById(999L));
    }
}