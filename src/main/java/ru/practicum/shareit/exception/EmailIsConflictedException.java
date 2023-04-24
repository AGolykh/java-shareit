package ru.practicum.shareit.exception;

public class EmailIsConflictedException extends NullPointerException {
    public EmailIsConflictedException(String s) {
        super(s);
    }
}