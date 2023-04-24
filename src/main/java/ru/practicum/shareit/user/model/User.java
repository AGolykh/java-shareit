package ru.practicum.shareit.user.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

@Data
@RequiredArgsConstructor
public class User {

    @Positive(message = "Идентификатор пользователя не может быть отрицательным.")
    private Long id;

    @NotBlank(message = "Имя пользователя не может быть пустым.")
    @Length(max = 50, message = "Имя пользователя не должно превышать 50 символов.")
    private String name;

    @NotBlank(message = "Почта пользователя не может быть пустой.")
    @Email(message = "Некорректный формат почтового адреса.")
    private String email;
}
