package ru.practicum.shareit.exception;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class IsNotCreatedException extends RuntimeException {

    public IsNotCreatedException(String s) {
        super(s);
    }
}
