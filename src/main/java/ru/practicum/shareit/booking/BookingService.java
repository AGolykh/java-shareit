package ru.practicum.shareit.booking;

import java.util.List;

public interface BookingService {
    List<BookingDtoModel> getByUserId(Long userId);

    BookingDtoModel getById(Long itemId);

    BookingDtoModel create(Long userId, BookingDtoId bookingDtoId);

    BookingDtoModel update(Long userId, Long itemId, BookingDtoModel itemDto);

    BookingDtoModel approve(Long userId, Long bookingId, Boolean isApproved);
}
