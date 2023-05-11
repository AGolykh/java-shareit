package ru.practicum.shareit.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ObjectNotFoundException extends NullPointerException {

    public ObjectNotFoundException(String object, Long objectId) {
        super(String.format("%s %d is not found.", object, objectId));
        log.warn("{} {} is not found.", object, objectId);
    }
}
