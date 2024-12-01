package ru.yandex.practicum.filmorate.dal.mappers;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.film.FilmLikes;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class FilmLikesRowMapper implements RowMapper<FilmLikes> {
    @Override
    public FilmLikes mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        FilmLikes filmLikes = new FilmLikes();
        filmLikes.setId_film(resultSet.getInt("id_film"));
        filmLikes.setId_user(resultSet.getInt("id_user"));
        return filmLikes;
    }
}
