package ru.practicum.shareit.booking;

import java.util.List;

public interface BookingService {

    List<BookingDtoModel> getByBookerId(Long userId, String subState);

    List<BookingDtoModel> getByOwnerId(Long ownerId, String subState);

    BookingDtoModel getById(Long userId, Long itemId);

    BookingDtoModel create(Long userId, BookingDtoId bookingDtoId);

    BookingDtoModel update(Long userId, Long bookingId, BookingDtoId bookingDtoId);

    BookingDtoModel approve(Long userId, Long bookingId, Boolean isApproved);

}
