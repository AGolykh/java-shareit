package ru.practicum.shareit.item.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Length;
import ru.practicum.shareit.request.model.ItemRequest;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
@RequiredArgsConstructor
public class Item {

    @Positive(message = "Идентификатор предмета не может быть отрицательным.")
    private Long id;

    @NotBlank(message = "Название предмета не может быть пустым.")
    @Length(max = 100, message = "Название предмета не должно превышать 100 символов.")
    private String name;

    @NotBlank(message = "Описание предмета не может быть пустым.")
    @Length(max = 100, message = "Описание предмета не должно превышать 100 символов.")
    private String description;

    @NotNull
    private boolean available;

    private Long owner;

    private ItemRequest request;
}