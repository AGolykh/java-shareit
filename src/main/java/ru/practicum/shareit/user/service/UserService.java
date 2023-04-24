package ru.practicum.shareit.user.service;

import ru.practicum.shareit.user.model.User;

import java.util.List;

interface UserService {
    List<User> getAllUsers();
    User saveUser(User user);
}