package ru.practicum.shareit.user;

import ru.practicum.shareit.user.dto.UserFullDto;
import ru.practicum.shareit.user.dto.UserInputDto;
import ru.practicum.shareit.user.dto.UserUpdateDto;

import java.util.List;

public interface UserService {

    List<UserFullDto> getAll();

    UserFullDto getById(Long userId);

    UserFullDto create(UserInputDto userInputDto);

    UserFullDto update(UserUpdateDto userUpdateDto, Long id);

    void deleteById(Long userId);

    User getUserById(Long userId);
}