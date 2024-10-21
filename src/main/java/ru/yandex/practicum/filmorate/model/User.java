package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Past;
import lombok.Data;
import ru.yandex.practicum.filmorate.validator.login.LoginContainSpaces;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
public class User {
    private int id;
    @Email
    private String email;
    @LoginContainSpaces
    private String login;
    private String name;
    @Past
    private LocalDate birthday;
    private Set<Integer> friendsList;

    public User() {
    }

    public User(LocalDate birthday, String name, String login, String email) {
        this.birthday = birthday;
        this.name = name;
        this.login = login;
        this.email = email;
    }

    public Set<Integer> getFriendsList() {
        if (friendsList == null) {
            return new HashSet<>();
        }
        return friendsList;
    }
}
