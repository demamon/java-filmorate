package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import lombok.Data;

import java.time.LocalDate;

@Data
public class User {
    private int id;
    @Email
    private String email;
    @NotBlank
    private String login;
    private String name;
    @Past
    private LocalDate birthday;

    public User() {
    }

    public User(LocalDate birthday, String name, String login, String email) {
        this.birthday = birthday;
        this.name = name;
        this.login = login;
        this.email = email;
    }
}
