package ru.practicum.shareit.booking;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.booking.BookingCreationException;
import ru.practicum.shareit.exception.booking.BookingNotFoundException;
import ru.practicum.shareit.exception.booking.ItemUnavailableException;
import ru.practicum.shareit.exception.item.ItemNotFoundException;
import ru.practicum.shareit.exception.item.WrongOwnerException;
import ru.practicum.shareit.item.Item;
import ru.practicum.shareit.item.ItemService;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserService;
import ru.practicum.shareit.validators.DateTimeValidator;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final ItemService itemService;
    private final UserService userService;

    @Override
    public List<BookingDtoModel> getByUserId(Long userId) {
        return null;
    }

    @Override
    public BookingDtoModel getById(Long bookingId) {
        return null;
    }

    @Override
    public BookingDtoModel create(Long userId, BookingDtoId bookingDtoId) {
        User booker = userService.getUserById(userId);
        Item item = itemService.getItemById(bookingDtoId.getItemId());
        if(!item.isAvailable()) {
            throw new ItemUnavailableException(item.getId());
        }
        DateTimeValidator.validate(bookingDtoId.getStart(), bookingDtoId.getEnd());

        Booking booking = new Booking();
        booking.setItem(item);
        booking.setBooker(booker);
        BookingDtoModel result =
                Optional.of(bookingRepository.save(BookingMapper.mapToBooking(bookingDtoId, booking)))
                .map(BookingMapper::mapToDtoModel)
                .orElseThrow(() -> new BookingCreationException(booking.getItem().getName()));
        log.info("Booking {} {} created.", result.getId(), result.getItem().getName());
        return result;
    }

    @Override
    public BookingDtoModel update(Long userId, Long itemId, BookingDtoModel itemDto) {
        return null;
    }

    @Override
    public BookingDtoModel approve(Long userId, Long bookingId, Boolean isApproved) {
        User owner = userService.getUserById(userId);
        Booking booking = getBookingById(bookingId);
        Item item = itemService.getItemById(booking.getItem().getId());
        if(!owner.getId().equals(item.getOwner())) {
            throw new WrongOwnerException(userId, item.getId());
        }

        booking.setStatus(isApproved ? StatusType.APPROVED : StatusType.REJECTED);
        return BookingMapper.mapToDtoModel(booking);
    }


    public Booking getBookingById(Long bookingId) {
        return bookingRepository
                .findById(bookingId)
                .orElseThrow(() -> new BookingNotFoundException(bookingId));
    }
}
