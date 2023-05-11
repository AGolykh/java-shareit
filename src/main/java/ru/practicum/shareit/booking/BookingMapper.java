package ru.practicum.shareit.booking;

public class BookingMapper {

    public static BookingDtoModel mapToDtoModel(Booking booking) {
        return new BookingDtoModel(booking.getId(),
                booking.getStart(),
                booking.getEnd(),
                booking.getItem(),
                booking.getBooker(),
                booking.getStatus());
    }

    public static Booking mapToBooking(BookingDtoId bookingDtoId, Booking booking) {
        if (bookingDtoId.getId() != null) {
            booking.setId(bookingDtoId.getId());
        }

        if (bookingDtoId.getStart() != null) {
            booking.setStart(bookingDtoId.getStart());
        }

        if (bookingDtoId.getEnd() != null) {
            booking.setEnd(bookingDtoId.getEnd());
        }

        if (bookingDtoId.getStatus() != null) {
            booking.setStatus(bookingDtoId.getStatus());
        } else {
            booking.setStatus(Status.WAITING);
        }

        return booking;
    }

}
