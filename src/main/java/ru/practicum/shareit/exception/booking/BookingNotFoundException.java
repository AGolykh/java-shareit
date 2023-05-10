package ru.practicum.shareit.exception.booking;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BookingNotFoundException extends NullPointerException {

    public BookingNotFoundException(Long bookingId) {
        super(String.format("Booking %d is not found.", bookingId));
        log.warn("Booking {} is not found.", bookingId);
    }
}
