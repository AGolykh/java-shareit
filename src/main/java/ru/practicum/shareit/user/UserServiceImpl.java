package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.exception.ObjectCreationException;
import ru.practicum.shareit.exception.ObjectNotFoundException;

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
        UserDto result = userRepository
                .findById(userId)
                .map(UserMapper::mapToDto)
                .orElseThrow(() -> new ObjectNotFoundException("User", userId));
        log.info("User {} is found.", result.getId());
        return result;
    }

    @Transactional
    public UserDto create(UserDto userDto) {
        User user = UserMapper.mapToUser(userDto, new User());
        UserDto result = Optional.of(userRepository.save(user))
                .map(UserMapper::mapToDto)
                .orElseThrow(() -> new ObjectCreationException("User", user.getName()));
        log.info("User {} {} created.", result.getId(), result.getName());
        return result;
    }

    @Transactional
    public UserDto update(UserDto newUser, Long userId) {
        User oldUser = getUserById(userId);
        UserDto result = Optional.of(userRepository.save(UserMapper.mapToUser(newUser, oldUser)))
                .map(UserMapper::mapToDto)
                .orElseThrow(() -> new ObjectNotFoundException("User", userId));
        log.info("User {} {} updated.", result.getId(), result.getName());
        return result;
    }

    @Transactional
    public void deleteById(Long userId) {
        User result = getUserById(userId);
        userRepository.deleteById(result.getId());
        log.info("User {} removed.", result.getName());
    }

    public User getUserById(Long userId) {
        User result = userRepository
                .findById(userId)
                .orElseThrow(() -> new ObjectNotFoundException("User", userId));
        log.info("User {} is found.", result.getId());
        return result;
    }
}