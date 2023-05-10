package ru.practicum.shareit.booking;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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


/*    @PatchMapping("/{itemId}")
    public ItemDto update(@RequestHeader("X-Sharer-User-Id") Long userId,
                          @RequestBody ItemDto itemDto,
                          @PathVariable Long itemId) {
        return bookingService.update(userId, itemId, itemDto);
    }*/
}
