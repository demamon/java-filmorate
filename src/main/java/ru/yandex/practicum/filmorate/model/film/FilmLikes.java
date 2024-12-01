package ru.yandex.practicum.filmorate.model.film;

import lombok.Data;

@Data
public class FilmLikes {
    private int idFilm;
    private int idUser;
}
