package ru.practicum.shareit.item;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Data
@RequiredArgsConstructor
@Entity
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