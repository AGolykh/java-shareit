package ru.practicum.shareit.user.repository;

import ru.practicum.shareit.user.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    List<User> findAll();

    Optional<User> findById(Long userId);

    Optional<User> create(User user);

    Optional<User> update(User user);

    void removeById(Long userId);

    Boolean emailIsExist(String email);
}