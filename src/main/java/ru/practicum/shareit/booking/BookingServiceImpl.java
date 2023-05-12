package ru.practicum.shareit.booking;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.booking.dto.*;
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
    public List<BookingFullDto> getByBookerId(Long bookerId, String subState) {
        State state = getState(subState);
        User booker = userService.getUserById(bookerId);
        List<BookingFullDto> result = (switch (state) {
            case ALL -> bookingRepository.findAllByBookerIdOrderByStartDesc(booker.getId());
            case CURRENT -> bookingRepository.findAllByBookerIdAndStateCurrentOrderByStartDesc(booker.getId());
            case PAST -> bookingRepository.findAllByBookerIdAndStatePastOrderByStartDesc(booker.getId());
            case FUTURE -> bookingRepository.findAllByBookerIdAndStateFutureOrderByStartDesc(booker.getId());
            case WAITING, REJECTED -> bookingRepository.findAllByBookerIdAndStatusOrderByStartDesc(booker.getId(),
                    Status.valueOf(state.toString()));
        }).stream()
                .map(BookingMapper::mapToFullDto)
                .collect(Collectors.toList());

        log.info("Found {} booking(s).", result.size());
        return result;
    }

    @Override
    public List<BookingFullDto> getByOwnerId(Long ownerId, String subState) {
        State state = getState(subState);
        User owner = userService.getUserById(ownerId);
        List<BookingFullDto> result = (switch (state) {
            case ALL -> bookingRepository.findAllByOwnerIdOrderByStartDesc(owner.getId());
            case CURRENT -> bookingRepository.findAllByOwnerIdAndStateCurrentOrderByStartDesc(owner.getId());
            case PAST -> bookingRepository.findAllByOwnerIdAndStatePastOrderByStartDesc(owner.getId());
            case FUTURE -> bookingRepository.findAllByOwnerIdAndStateFutureOrderByStartDesc(owner.getId());
            case WAITING, REJECTED -> bookingRepository.findAllByOwnerIdAndStatusOrderByStartDesc(owner.getId(),
                    Status.valueOf(state.toString()));
        }).stream()
                .map(BookingMapper::mapToFullDto)
                .collect(Collectors.toList());

        log.info("Found {} booking(s).", result.size());
        return result;
    }

    @Override
    public BookingFullDto getById(Long userId, Long bookingId) {
        Booking booking = getBookingById(bookingId);
        User booker = booking.getBooker();
        User owner = userService.getUserById(booking.getItem().getOwner().getId());
        if (!booker.getId().equals(userId) && !owner.getId().equals(userId)) {
            throw new WrongOwnerException(userId, "Booking", bookingId);
        }
        BookingFullDto result = bookingRepository.findById(bookingId)
                .map(BookingMapper::mapToFullDto)
                .orElseThrow(() -> new ObjectNotFoundException("Booking", bookingId));
        log.info("Booking {} is found.", result.getId());
        return result;
    }

    @Transactional
    @Override
    public BookingFullDto create(Long userId, BookingInputDto bookingInputDto) {
        User booker = userService.getUserById(userId);
        Item item = itemService.getItemById(bookingInputDto.getItemId());
        if (booker.getId().equals(item.getOwner().getId())) {
            throw new WrongBookerException();
        }

        if (!item.isAvailable()) {
            throw new ItemUnavailableException(item.getId());
        }

        DateTimeValidator.validate(bookingInputDto.getStart(), bookingInputDto.getEnd());

        Booking booking = new Booking();
        booking.setItem(item);
        booking.setBooker(booker);
        BookingFullDto result =
                Optional.of(bookingRepository.save(BookingMapper.mapToBooking(bookingInputDto, booking)))
                        .map(BookingMapper::mapToFullDto)
                        .orElseThrow(() -> new ObjectCreationException("Booking", booking.getItem().getName()));
        log.info("Booking {} {} created.", result.getId(), result.getItem().getName());
        return result;
    }

    @Transactional
    @Override
    public BookingFullDto approve(Long userId, Long bookingId, Boolean isApproved) {
        User owner = userService.getUserById(userId);
        Booking booking = getBookingById(bookingId);
        Item item = itemService.getItemById(booking.getItem().getId());
        Status newStatus = isApproved ? Status.APPROVED : Status.REJECTED;

        if (!booking.getStatus().equals(Status.WAITING) && owner.getId().equals(item.getOwner().getId())) {
            throw new ObjectUpdateException("Booking", booking.getId().toString());
        }
        if (!owner.getId().equals(item.getOwner().getId())) {
            throw new WrongOwnerException(userId, "Item", item.getId());
        }

        booking.setStatus(newStatus);
        log.info("Booking status changed to {}.", booking.getStatus());
        return BookingMapper.mapToFullDto(booking);
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
