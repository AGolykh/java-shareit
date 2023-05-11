package ru.practicum.shareit.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ItemUnavailableException extends RuntimeException {

    public ItemUnavailableException(Long itemId) {
        super(String.format("Item %d is unavailable.", itemId));
        log.warn("Item {} is unavailable.", itemId);
    }
}
