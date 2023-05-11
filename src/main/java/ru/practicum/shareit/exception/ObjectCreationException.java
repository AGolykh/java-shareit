package ru.practicum.shareit.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ObjectCreationException extends RuntimeException {

    public ObjectCreationException(String object,String text) {
        super(String.format("%s for item %s not created.", object, text));
        log.warn("{} for item {} not created.", object, text);
    }
}

