package ru.practicum.shareit.user;

public class UserMapper {
    public static UserDto mapToDto(User user) {
        return new UserDto(user.getId(), user.getName(), user.getEmail());
    }

    public static User mapToUser(UserDto userDto, User user) {
        User newUser = new User();
        if (userDto.getId() != null) {
            newUser.setId(userDto.getId());
        } else {
            newUser.setId(user.getId());
        }

        if (userDto.getName() != null) {
            newUser.setName(userDto.getName());
        } else {
            newUser.setName(user.getName());
        }

        if (userDto.getEmail() != null) {
            newUser.setEmail(userDto.getEmail());
        } else {
            newUser.setEmail(user.getEmail());
        }
        return newUser;
    }
}