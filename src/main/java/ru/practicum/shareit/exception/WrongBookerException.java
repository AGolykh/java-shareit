package ru.practicum.shareit.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class WrongBookerException extends RuntimeException {
    public WrongBookerException() {
        super("The user cannot book his item.");
        log.warn("The user cannot book his item.");
    }
}
