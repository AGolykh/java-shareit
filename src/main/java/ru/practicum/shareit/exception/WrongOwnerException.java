package ru.practicum.shareit.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class WrongOwnerException extends RuntimeException {

    public WrongOwnerException(Long userId, String object, Long objectId) {
        super(String.format("User %d is not the owner of the %s %d.", userId, object, objectId));
        log.warn("User {} is not the owner of the {} {}.", userId, object, objectId);
    }
}