package ru.practicum.shareit.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ObjectUpdateException extends RuntimeException {

    public ObjectUpdateException(String object,String text) {
        super(String.format("%s for item %s not created.", object, text));
        log.warn("{} for item {} not created.", object, text);
    }
}
