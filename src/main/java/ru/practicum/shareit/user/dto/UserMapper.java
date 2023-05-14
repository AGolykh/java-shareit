package ru.practicum.shareit.user.dto;

import ru.practicum.shareit.user.User;

public class UserMapper {
    public static UserFullDto mapToFullDto(User user) {
        return new UserFullDto(user.getId(),
                user.getName(),
                user.getEmail());
    }

    public static UserShortDto mapToShortDto(User user) {
        return new UserShortDto(user.getId(),
                user.getName());
    }

    public static User mapToNewUser(UserInputDto userInputDto, User user) {
        user.setName(userInputDto.getName());
        user.setEmail(userInputDto.getEmail());

        return user;
    }

    public static User mapToUpdateUser(UserUpdateDto userUpdateDto, User user) {
        if (userUpdateDto.getName() == null && userUpdateDto.getEmail() == null) {
            //qqqqqqqqqqqqqqqqqqqqqqqqqqq
        }

        if (userUpdateDto.getName() != null) {
            user.setName(userUpdateDto.getName());
        }

        if (userUpdateDto.getEmail() != null) {
            user.setEmail(userUpdateDto.getEmail());
        }

        return user;
    }
}