package ru.practicum.shareit.user.repository;

import ru.practicum.shareit.user.model.User;

import java.util.List;

interface UserRepository {
    List<User> findAll();
    User save(User user);
}