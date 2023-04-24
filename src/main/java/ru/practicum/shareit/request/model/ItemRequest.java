package ru.practicum.shareit.request.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Length;
import ru.practicum.shareit.user.model.User;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.time.LocalDateTime;

@Data
@RequiredArgsConstructor
public class ItemRequest {

    @Positive(message = "Идентификатор запроса не может быть отрицательным.")
    private final Long id;


    @NotBlank(message = "Описание запроса не может быть пустым.")
    @Length(max = 255, message = "Описание запроса не должно превышать 255 символов.")
    private String description;

    private User requestor;

    private LocalDateTime created;
}
