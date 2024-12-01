package ru.yandex.practicum.filmorate.model.film;

import lombok.Data;

@Data
public class FilmLikes {
    private int id_film;
    private int id_user;
}
