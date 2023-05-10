package ru.practicum.shareit.validators;

import ru.practicum.shareit.exception.booking.ValidateTimeException;

import java.time.LocalDateTime;

public class DateTimeValidator {

    public static void validate(LocalDateTime start, LocalDateTime end) {
        if (end.isBefore(start)) {
            throw new ValidateTimeException("The end date cannot be earlier than the start date");
        }

        if (end.isBefore(LocalDateTime.now())) {
            throw new ValidateTimeException("The end date cannot be earlier than the current date.");
        }

        if (start.isBefore(LocalDateTime.now())) {
            throw new ValidateTimeException("The start date cannot be earlier than the current date.");
        }

        if (end.isEqual(start)) {
            throw new ValidateTimeException("The end date cannot be equal to the start date.");
        }
    }
}
