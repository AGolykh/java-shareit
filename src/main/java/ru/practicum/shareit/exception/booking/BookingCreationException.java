package ru.practicum.shareit.exception.booking;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BookingCreationException extends RuntimeException {

    public BookingCreationException(String name) {
        super(String.format("Booking for item %s not created.", name));
        log.warn("Booking for item {} not created.", name);
    }
}

