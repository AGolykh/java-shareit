package ru.practicum.shareit.item;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import ru.practicum.shareit.booking.Booking;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Data
@RequiredArgsConstructor
@Entity
@EqualsAndHashCode
@Table(name = "ITEMS", schema = "PUBLIC")
public class Item {

    @Id
    @Column(name = "ITEM_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ITEM_NAME")
    private String name;

    private String description;

    private boolean available;

    @Column(name = "OWNER_ID")
    private Long owner;
}