package ru.practicum.shareit.booking;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.booking.dto.BookingIdDto;
import ru.practicum.shareit.booking.dto.BookingMapper;
import ru.practicum.shareit.booking.dto.BookingObjectDto;
import ru.practicum.shareit.exception.*;
import ru.practicum.shareit.item.Item;
import ru.practicum.shareit.item.ItemService;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserService;
import ru.practicum.shareit.validators.DateTimeValidator;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final ItemService itemService;
    private final UserService userService;

    @Override
    public List<BookingObjectDto> getByBookerId(Long bookerId, String subState) {
        State state = getState(subState);
        User booker = userService.getUserById(bookerId);
        List<BookingObjectDto> result = (switch (state) {
            case ALL -> bookingRepository.findAllByBookerIdOrderByStartDesc(booker.getId());
            case CURRENT -> bookingRepository.findAllByBookerIdAndStateCurrentOrderByStartDesc(booker.getId());
            case PAST -> bookingRepository.findAllByBookerIdAndStatePastOrderByStartDesc(booker.getId());
            case FUTURE -> bookingRepository.findAllByBookerIdAndStateFutureOrderByStartDesc(booker.getId());
            case WAITING, REJECTED -> bookingRepository.findAllByBookerIdAndStatusOrderByStartDesc(booker.getId(),
                    Status.valueOf(state.toString()));
        }).stream()
                .map(BookingMapper::mapToDtoModel)
                .collect(Collectors.toList());

        log.info("Found {} booking(s).", result.size());
        return result;
    }

    @Override
    public List<BookingObjectDto> getByOwnerId(Long ownerId, String subState) {
        State state = getState(subState);
        User owner = userService.getUserById(ownerId);
        List<BookingObjectDto> result = (switch (state) {
            case ALL -> bookingRepository.findAllByOwnerIdOrderByStartDesc(owner.getId());
            case CURRENT -> bookingRepository.findAllByOwnerIdAndStateCurrentOrderByStartDesc(owner.getId());
            case PAST -> bookingRepository.findAllByOwnerIdAndStatePastOrderByStartDesc(owner.getId());
            case FUTURE -> bookingRepository.findAllByOwnerIdAndStateFutureOrderByStartDesc(owner.getId());
            case WAITING, REJECTED -> bookingRepository.findAllByOwnerIdAndStatusOrderByStartDesc(owner.getId(),
                    Status.valueOf(state.toString()));
        }).stream()
                .map(BookingMapper::mapToDtoModel)
                .collect(Collectors.toList());

        log.info("Found {} booking(s).", result.size());
        return result;
    }

    @Override
    public BookingObjectDto getById(Long userId, Long bookingId) {
        Booking booking = getBookingById(bookingId);
        User booker = booking.getBooker();
        User owner = userService.getUserById(booking.getItem().getOwner());
        if (!booker.getId().equals(userId) && !owner.getId().equals(userId)) {
            throw new WrongOwnerException(userId, "Booking", bookingId);
        }
        BookingObjectDto result = bookingRepository.findById(bookingId)
                .map(BookingMapper::mapToDtoModel)
                .orElseThrow(() -> new ObjectNotFoundException("Booking", bookingId));
        log.info("Booking {} is found.", result.getId());
        return result;
    }

    @Transactional
    @Override
    public BookingObjectDto create(Long userId, BookingIdDto bookingIdDto) {
        User booker = userService.getUserById(userId);
        Item item = itemService.getItemById(bookingIdDto.getItemId());
        if (booker.getId().equals(item.getOwner())) {
            throw new WrongBookerException();
        }

        if (!item.isAvailable()) {
            throw new ItemUnavailableException(item.getId());
        }

        DateTimeValidator.validate(bookingIdDto.getStart(), bookingIdDto.getEnd());

        Booking booking = new Booking();
        booking.setItem(item);
        booking.setBooker(booker);
        BookingObjectDto result =
                Optional.of(bookingRepository.save(BookingMapper.mapToBooking(bookingIdDto, booking)))
                        .map(BookingMapper::mapToDtoModel)
                        .orElseThrow(() -> new ObjectCreationException("Booking", booking.getItem().getName()));
        log.info("Booking {} {} created.", result.getId(), result.getItem().getName());
        return result;
    }

    @Transactional
    @Override
    public BookingObjectDto update(Long userId, Long itemId, BookingIdDto bookingIdDto) {
        return null;
    }

    @Transactional
    @Override
    public BookingObjectDto approve(Long userId, Long bookingId, Boolean isApproved) {
        User owner = userService.getUserById(userId);
        Booking booking = getBookingById(bookingId);
        Item item = itemService.getItemById(booking.getItem().getId());
        Status newStatus = isApproved ? Status.APPROVED : Status.REJECTED;

        if (!booking.getStatus().equals(Status.WAITING) && owner.getId().equals(item.getOwner())) {
            throw new ObjectUpdateException("Booking", booking.getId().toString());
        }
        if (!owner.getId().equals(item.getOwner())) {
            throw new WrongOwnerException(userId, "Item", item.getId());
        }

        booking.setStatus(newStatus);
        log.info("Booking status changed to {}.", booking.getStatus());
        return BookingMapper.mapToDtoModel(booking);
    }


    public Booking getBookingById(Long bookingId) {
        return bookingRepository
                .findById(bookingId)
                .orElseThrow(() -> new ObjectNotFoundException("Object", bookingId));
    }

    private State getState(String state) {
        try {
            return State.valueOf(state);
        } catch (IllegalArgumentException e) {
            throw new UnknownStateException(state);
        }
    }
}
