package ru.practicum.shareit.booking;

import ru.practicum.shareit.booking.dto.BookingIdDto;
import ru.practicum.shareit.booking.dto.BookingObjectDto;

import java.util.List;

public interface BookingService {

    List<BookingObjectDto> getByBookerId(Long userId, String subState);

    List<BookingObjectDto> getByOwnerId(Long ownerId, String subState);

    BookingObjectDto getById(Long userId, Long itemId);

    BookingObjectDto create(Long userId, BookingIdDto bookingIdDto);

    BookingObjectDto update(Long userId, Long bookingId, BookingIdDto bookingIdDto);

    BookingObjectDto approve(Long userId, Long bookingId, Boolean isApproved);

}
