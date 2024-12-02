package ru.yandex.practicum.filmorate.dal;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.film.GenreFilm;

import java.util.List;

@Repository
public class GenreFilmRepository extends BaseRepository<GenreFilm> {
    private static final String FIND_ALL_QUERY = "SELECT * FROM film_genres";
    private static final String FIND_BY_ID_QUERY = "SELECT * FROM film_genres WHERE id_film = ?";
    private static final String INSERT_QUERY = "INSERT INTO film_genres(id_film, id_genre) VALUES (?, ?)";
    private static final String DELETE_QUERY = "DELETE FROM film_genres WHERE id_film = ?";

    public GenreFilmRepository(JdbcTemplate jdbc, RowMapper<GenreFilm> mapper) {
        super(jdbc, mapper);
    }

    public List<GenreFilm> findAll() {
        return findMany(FIND_ALL_QUERY);
    }

    public List<GenreFilm> findByFilm(int id) {
        return findMany(FIND_BY_ID_QUERY, id);
    }

    public void save(int idFilm, int idGenre) {
        update(
                INSERT_QUERY,
                idFilm,
                idGenre
        );
    }

    public void delete(int idFilm) {
        update(
                DELETE_QUERY,
                idFilm
        );
    }
}
