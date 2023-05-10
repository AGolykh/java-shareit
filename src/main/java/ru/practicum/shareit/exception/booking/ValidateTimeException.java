package ru.practicum.shareit.exception.booking;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ValidateTimeException extends RuntimeException {

    public ValidateTimeException(String string) {
            super(string);
            log.warn(string);
        }
}
