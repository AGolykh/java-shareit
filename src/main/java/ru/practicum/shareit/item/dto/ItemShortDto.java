package ru.practicum.shareit.item.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ItemShortDto {

    private Long id;
    private String name;
    private String description;
    private Boolean available;
    private Long requestId;

    public ItemShortDto(String name, String description, Boolean available, Long requestId) {
        this.name = name;
        this.description = description;
        this.available = available;
        this.requestId = requestId;
    }

    public ItemShortDto(Long id, String name, String description, Boolean available, Long requestId) {
        this(name, description, available, requestId);
        this.id = id;
    }
}
