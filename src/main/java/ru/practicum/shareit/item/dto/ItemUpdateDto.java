package ru.practicum.shareit.item.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
public class ItemUpdateDto {

    @NotBlank
    private Long itemId;

    private String name;

    private String description;

    private Boolean available;
}