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

    public static BookingDtoId mapToDtoId(Booking booking) {
        return new BookingDtoId(booking.getId(),
                booking.getStart(),
                booking.getEnd(),
                booking.getItem().getId(),
                booking.getBooker().getId(),
                booking.getStatus());
    }

    public static Booking mapToBooking(BookingDtoModel bookingDtoModel, Booking booking) {
        if (bookingDtoModel.getId() != null) {
            booking.setId(bookingDtoModel.getId());
        }

        if (bookingDtoModel.getStart() != null) {
            booking.setStart(bookingDtoModel.getStart());
        }

        if (bookingDtoModel.getEnd() != null) {
            booking.setEnd(bookingDtoModel.getEnd());
        }

        if (bookingDtoModel.getItem() != null) {
            booking.setItem(bookingDtoModel.getItem());
        }

        if (bookingDtoModel.getBooker() != null) {
            booking.setBooker(bookingDtoModel.getBooker());
        }

        if (bookingDtoModel.getStatus() != null) {
            booking.setStatus(bookingDtoModel.getStatus());
        } else {
            booking.setStatus(StatusType.WAITING);
        }

        return booking;
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
            booking.setStatus(StatusType.WAITING);
        }

        return booking;
    }

}
