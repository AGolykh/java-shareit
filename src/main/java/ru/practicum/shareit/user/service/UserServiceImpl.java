package ru.practicum.shareit.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.EmailIsConflictedException;
import ru.practicum.shareit.exception.IncorrectObjectIdException;
import ru.practicum.shareit.exception.IsNotCreatedException;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.dto.UserMapper;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public List<UserDto> getAll() {
        List<UserDto> result = userRepository.findAll()
                .stream()
                .map(UserMapper::mapToDto)
                .collect(Collectors.toList());
        log.info("Found {} user(s).", result.size());
        return result;
    }

    public UserDto getById(Long userId) {
        Optional<User> result = userRepository.findById(userId);
        if (result.isEmpty()) {
            log.warn("User {} is not found.", userId);
            throw new IncorrectObjectIdException(String.format("User %d is not found.", userId));
        }
        log.info("User {} is found.", result.get().getId());
        return UserMapper.mapToDto(result.get());
    }

    public UserDto create(UserDto userDto) {
        User user = UserMapper.mapToUser(userDto, new User());
        Optional<User> result = userRepository.create(user);
        if (result.isEmpty()) {
            log.warn("User {} is not created.",
                    user.getName());
            throw new IsNotCreatedException(String.format("User %s is not created.",
                    user.getName()));
        }
        log.info("User {} {} created.",
                result.get().getId(), result.get().getName());
        return UserMapper.mapToDto(result.get());
    }

    public UserDto update(UserDto newUser, Long userId) {
        Optional<User> oldUser = userRepository.findById(userId);
        if (oldUser.isEmpty()) {
            log.warn("User {} is not found.", userId);
            throw new IncorrectObjectIdException(String.format("User %d is not found.", userId));
        }

        String email = newUser.getEmail();
        if (email != null
                && !oldUser.get().getEmail().equalsIgnoreCase(email)
                && userRepository.emailIsExist(email)) {
            log.warn("User's email {} is wrong.", newUser.getEmail());
            throw new EmailIsConflictedException(String.format("User's email %s is wrong.", newUser.getEmail()));
        }

        Optional<User> result = userRepository.update(UserMapper.mapToUser(newUser, oldUser.get()));
        if (result.isEmpty()) {
            log.warn("User {} {} is not updated.",
                    newUser.getId(), newUser.getName());
            throw new IncorrectObjectIdException(String.format("User %d %s is not updated.",
                    newUser.getId(), newUser.getName()));
        }

        log.info("User {} {} updated.",
                result.get().getId(), result.get().getName());
        return UserMapper.mapToDto(result.get());
    }


    public void deleteById(Long userId) {
        Optional<User> result = userRepository.findById(userId);
        if (result.isEmpty()) {
            log.warn("User {} is not found.", userId);
            throw new IncorrectObjectIdException(String.format("User %s is not found.", userId));
        }
        userRepository.removeById(userId);
        log.info("User {} removed.", result.get().getName());
    }
}