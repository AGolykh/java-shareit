package ru.practicum.shareit.exception;

import lombok.AllArgsConstructor;


@AllArgsConstructor
public class IncorrectObjectIdException extends NullPointerException {
    public IncorrectObjectIdException(String s) {
        super(s);
    }
}
