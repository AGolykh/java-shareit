package ru.practicum.shareit.booking.dto;

import ru.practicum.shareit.booking.Booking;
import ru.practicum.shareit.booking.Status;

public class BookingMapper {

    public static BookingObjectDto mapToDtoModel(Booking booking) {
        return new BookingObjectDto(booking.getId(),
                booking.getStart(),
                booking.getEnd(),
                booking.getItem(),
                booking.getBooker(),
                booking.getStatus());
    }

    public static BookingIdDto mapToDtoId(Booking booking) {
        return new BookingIdDto(booking.getId(),
                booking.getStart(),
                booking.getEnd(),
                booking.getItem().getId(),
                booking.getBooker().getId(),
                booking.getStatus());
    }

    public static Booking mapToBooking(BookingIdDto bookingIdDto, Booking booking) {
        if (bookingIdDto.getId() != null) {
            booking.setId(bookingIdDto.getId());
        }

        if (bookingIdDto.getStart() != null) {
            booking.setStart(bookingIdDto.getStart());
        }

        if (bookingIdDto.getEnd() != null) {
            booking.setEnd(bookingIdDto.getEnd());
        }

        if (bookingIdDto.getStatus() != null) {
            booking.setStatus(bookingIdDto.getStatus());
        } else {
            booking.setStatus(Status.WAITING);
        }

        return booking;
    }

}
