package ru.practicum.shareit.item.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemDto {

    @Positive(message = "Идентификатор предмета не может быть отрицательным.")
    private Long id;

    @NotBlank(message = "Название предмета не может быть пустым.")
    @Length(max = 100, message = "Название предмета не должно превышать 100 символов.")
    private String name;

    @NotBlank(message = "Описание предмета не может быть пустым.")
    @Length(max = 100, message = "Описание предмета не должно превышать 100 символов.")
    private String description;

    @NotNull
    private Boolean available;

    public ItemDto(String name, String description, Boolean available) {
        this.name = name;
        this.description = description;
        this.available = available;
    }
}
