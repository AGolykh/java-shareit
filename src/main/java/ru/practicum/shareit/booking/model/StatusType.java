package ru.practicum.shareit.booking.model;

import lombok.RequiredArgsConstructor;

import java.util.Map;

@RequiredArgsConstructor
public enum StatusType {
    WAITING(1L),
    APPROVED(2L),
    REJECTED(3L),
    CANCELED(4L);

    private Long id;

    StatusType(Long id) {
        this.id = id;
    }
}
