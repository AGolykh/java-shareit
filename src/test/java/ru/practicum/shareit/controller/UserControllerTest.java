package ru.practicum.shareit.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserController;
import ru.practicum.shareit.user.UserService;
import ru.practicum.shareit.user.dto.UserInputDto;
import ru.practicum.shareit.user.dto.UserMapper;

import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(value = UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userServiceMock;


    @SneakyThrows
    @Test
    void getAll_return2User_add2User() {
        UserInputDto userInputDto1 = new UserInputDto();
        userInputDto1.setId(1L);
        userInputDto1.setName("viktor");
        userInputDto1.setEmail("dsfad@yandex.ru");

        UserInputDto userInputDto2 = new UserInputDto();
        userInputDto2.setId(2L);
        userInputDto2.setName("dsafdas");
        userInputDto2.setEmail("dfsafdsafda@yandex.ru");

        when(userServiceMock.getAll())
                .thenReturn(List.of(UserMapper.mapToFullDto(UserMapper.mapToUser(userInputDto1, new User())),
                        UserMapper.mapToFullDto(UserMapper.mapToUser(userInputDto2, new User()))));

        mockMvc.perform(get("/users/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(userInputDto1.getId()))
                .andExpect(jsonPath("$[0].email").value(userInputDto1.getEmail()))
                .andExpect(jsonPath("$[0].name").value((userInputDto1.getName())))
                .andExpect(jsonPath("$[1].id").value(userInputDto2.getId()))
                .andExpect(jsonPath("$[1].email").value(userInputDto2.getEmail()))
                .andExpect(jsonPath("$[1].name").value((userInputDto2.getName())));
        verify(userServiceMock).getAll();
    }

    @SneakyThrows
    @Test
    void getById_returnNewUser_addUser() {
        UserInputDto userInputDto = new UserInputDto();
        userInputDto.setId(1L);
        userInputDto.setName("viktor");
        userInputDto.setEmail("dsfad@yandex.ru");
        when(userServiceMock.getById(1L))
                .thenReturn(UserMapper.mapToFullDto(UserMapper.mapToUser(userInputDto, new User())));

        mockMvc.perform(get("/users/{id}", userInputDto.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(userInputDto.getId()))
                .andExpect(jsonPath("$.email").value(userInputDto.getEmail()))
                .andExpect(jsonPath("$.name").value((userInputDto.getName())));
        verify(userServiceMock).getById(userInputDto.getId());
    }

    @SneakyThrows
    @Test
    void getById_UserNotFound_returnNullPointerException() {
        UserInputDto userInputDto = new UserInputDto();
        userInputDto.setId(1L);
        userInputDto.setName("viktor");
        userInputDto.setEmail("dsfad@yandex.ru");
        when(userServiceMock.getById(1L)).thenThrow(NullPointerException.class);

        mockMvc.perform(get("/users/{id}", userInputDto.getId())).andExpect(status().isNotFound());
        verify(userServiceMock).getById(1L);
    }

    @SneakyThrows
    @Test
    void create_returnNewUser_addUser() {
        UserInputDto userInputDto = new UserInputDto();
        userInputDto.setId(1L);
        userInputDto.setName("viktor");
        userInputDto.setEmail("dsfad@yandex.ru");
        when(userServiceMock.create(userInputDto))
                .thenReturn(UserMapper.mapToFullDto(UserMapper.mapToUser(userInputDto, new User())));

        mockMvc.perform(post("/users")
                        .content(objectMapper.writeValueAsString(userInputDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(userInputDto.getId()))
                .andExpect(jsonPath("$.name").value(userInputDto.getName()))
                .andExpect(jsonPath("$.email").value(userInputDto.getEmail()));
        verify(userServiceMock).create(userInputDto);
    }

    @SneakyThrows
    @Test
    void update_returnNewUser_addUpdateUser() {
        UserInputDto userInputDto = new UserInputDto();
        userInputDto.setId(1L);
        userInputDto.setName("viktor");
        userInputDto.setEmail("dsfad@yandex.ru");
        when(userServiceMock.update(userInputDto, userInputDto.getId()))
                .thenReturn(UserMapper.mapToFullDto(UserMapper.mapToUser(userInputDto, new User())));

        mockMvc.perform(patch("/users/{id}", userInputDto.getId())
                        .content(objectMapper.writeValueAsString(userInputDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(userInputDto.getId()))
                .andExpect(jsonPath("$.name").value(userInputDto.getName()))
                .andExpect((jsonPath("$.email").value(userInputDto.getEmail())));
        verify(userServiceMock).update(userInputDto, userInputDto.getId());
    }

    @SneakyThrows
    @Test
    void deleteById() {
        UserInputDto userInputDto = new UserInputDto();
        userInputDto.setId(1L);
        userInputDto.setName("viktor");
        userInputDto.setEmail("dsfad@yandex.ru");
        mockMvc.perform(delete("/users/{id}", userInputDto.getId()))
                .andExpect(status().isOk());
        verify(userServiceMock).deleteById(userInputDto.getId());
    }

}
