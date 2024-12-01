package ru.yandex.practicum.filmorate.dal.mappers;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.dal.BaseRepository;
import ru.yandex.practicum.filmorate.model.film.FilmLikes;

import java.util.List;

@Repository
public class FilmLikesRepository extends BaseRepository<FilmLikes> {
    private static final String FIND_BY_ID_QUERY = "SELECT * FROM film_likes WHERE id_film = ?";
    private static final String INSERT_QUERY = "INSERT INTO film_likes(id_film, id_user) VALUES (?, ?)";
    private static final String DELETE_QUERY = "DELETE FROM film_likes WHERE id_film = ? AND id_user = ?";

    public FilmLikesRepository(JdbcTemplate jdbc, RowMapper<FilmLikes> mapper) {
        super(jdbc, mapper);
    }

    public List<FilmLikes> findByFilm(int idFilm) {
        return findMany(FIND_BY_ID_QUERY, idFilm);
    }

    public void save(int idFilm, int idUser) {
        update(
                INSERT_QUERY,
                idFilm,
                idUser
        );
    }

    public void delete(int idFilm, int idUser) {
        update(
                DELETE_QUERY,
                idFilm,
                idUser
        );
    }
}
