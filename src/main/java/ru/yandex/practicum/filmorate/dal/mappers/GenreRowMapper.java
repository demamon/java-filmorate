package ru.yandex.practicum.filmorate.dal.mappers;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.film.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class GenreRowMapper implements RowMapper<Genre> {
    @Override
    public Genre mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        Genre genre = new Genre();
        genre.setName(translate(resultSet.getString("name")));
        genre.setId(resultSet.getInt("genre_id"));
        return genre;
    }

    private String translate(String name) {
        return switch (name) {
            case "Comedy" -> "Комедия";
            case "Drama" -> "Драма";
            case "Cartoon" -> "Мультфильм";
            case "Thriller" -> "Триллер";
            case "Documentary" -> "Документальный";
            case "Action" -> "Боевик";
            default -> name;
        };
    }
}
