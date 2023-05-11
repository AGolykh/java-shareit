package ru.practicum.shareit.booking;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @PostMapping
    public BookingDtoModel create(@RequestHeader("X-Sharer-User-Id") Long userId,
                                  @Valid @RequestBody BookingDtoId bookingDtoid) {
        return bookingService.create(userId, bookingDtoid);
    }

    @PatchMapping("/{bookingId}")
    public BookingDtoModel approve(@RequestHeader("X-Sharer-User-Id") Long userId,
                                   @PathVariable Long bookingId,
                                   @RequestParam(name = "approved") Boolean isApproved) {
        return bookingService.approve(userId, bookingId, isApproved);
    }

    @GetMapping("/{bookingId}")
    public BookingDtoModel getById(@RequestHeader("X-Sharer-User-Id") Long userId,
                                   @PathVariable Long bookingId) {
        return bookingService.getById(userId, bookingId);
    }

    @GetMapping
    public List<BookingDtoModel> getByBookerId(
            @RequestHeader("X-Sharer-User-Id") Long bookerId,
            @RequestParam(name = "state", defaultValue = "ALL") String subState) {
        return bookingService.getByBookerId(bookerId, subState);
    }

    @GetMapping("/owner")
    public List<BookingDtoModel> getByOwnerId(
            @RequestHeader("X-Sharer-User-Id") Long ownerId,
            @RequestParam(name = "state", defaultValue = "ALL") String subState) {
        return bookingService.getByOwnerId(ownerId, subState);
    }
}
