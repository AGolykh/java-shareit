package ru.practicum.shareit.booking;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.dto.BookingIdDto;
import ru.practicum.shareit.booking.dto.BookingObjectDto;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @PostMapping
    public BookingObjectDto create(@RequestHeader("X-Sharer-User-Id") Long userId,
                                   @Valid @RequestBody BookingIdDto bookingDtoidDto) {
        return bookingService.create(userId, bookingDtoidDto);
    }

    @PatchMapping("/{bookingId}")
    public BookingObjectDto approve(@RequestHeader("X-Sharer-User-Id") Long userId,
                                    @PathVariable Long bookingId,
                                    @RequestParam(name = "approved") Boolean isApproved) {
        return bookingService.approve(userId, bookingId, isApproved);
    }

    @GetMapping("/{bookingId}")
    public BookingObjectDto getById(@RequestHeader("X-Sharer-User-Id") Long userId,
                                    @PathVariable Long bookingId) {
        return bookingService.getById(userId, bookingId);
    }

    @GetMapping
    public List<BookingObjectDto> getByBookerId(
            @RequestHeader("X-Sharer-User-Id") Long bookerId,
            @RequestParam(name = "state", defaultValue = "ALL") String subState) {
        return bookingService.getByBookerId(bookerId, subState);
    }

    @GetMapping("/owner")
    public List<BookingObjectDto> getByOwnerId(
            @RequestHeader("X-Sharer-User-Id") Long ownerId,
            @RequestParam(name = "state", defaultValue = "ALL") String subState) {
        return bookingService.getByOwnerId(ownerId, subState);
    }
}
