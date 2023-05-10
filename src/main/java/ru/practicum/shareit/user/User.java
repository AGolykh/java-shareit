package ru.practicum.shareit.user;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import ru.practicum.shareit.booking.Booking;
import ru.practicum.shareit.item.Item;
import ru.practicum.shareit.request.ItemRequest;

import javax.persistence.*;
import java.util.Set;

@Data
@RequiredArgsConstructor
@Entity
@Table(name = "USERS", schema = "PUBLIC")
public class User {

    @Id
    @Column(name = "USER_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "USER_NAME")
    private String name;

    private String email;

    @OneToMany
    @JoinColumn(name = "ITEM_ID")
    private Set<Item> ownerships;

    @OneToMany
    @JoinColumn(name = "REQUEST_ID")
    private Set<ItemRequest> requests;

    @OneToMany
    @JoinColumn(name = "BOOKING_ID")
    private Set<Booking> bookings;
}
