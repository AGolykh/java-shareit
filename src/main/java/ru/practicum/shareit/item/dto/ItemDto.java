package ru.practicum.shareit.item.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class ItemDto {

    private Long id;

    @NotBlank(message = "Название предмета не может быть пустым.")
    @Length(max = 100, message = "Название предмета не должно превышать 100 символов.")
    private String name;

    @NotBlank(message = "Описание предмета не может быть пустым.")
    @Length(max = 100, message = "Описание предмета не должно превышать 100 символов.")
    private String description;

    @NotNull
    private Boolean available;

    private Long ownerId;


    public ItemDto(String name, String description, Boolean available, Long ownerId) {
        this.name = name;
        this.description = description;
        this.available = available;
        this.ownerId = ownerId;
    }

    public ItemDto(Long id, String name, String description, Boolean available, Long ownerId) {
        this(name, description, available, ownerId);
        this.id = id;
    }
}
