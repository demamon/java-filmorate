package ru.yandex.practicum.filmorate.dal.mappers;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.film.GenreFilm;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class GenreFilmRowMapper implements RowMapper<GenreFilm> {
    @Override
    public GenreFilm mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        GenreFilm genreFilm = new GenreFilm();
        genreFilm.setIdGenre(resultSet.getInt("id_genre"));
        genreFilm.setIdFilm(resultSet.getInt("id_film"));
        return genreFilm;
    }
}
