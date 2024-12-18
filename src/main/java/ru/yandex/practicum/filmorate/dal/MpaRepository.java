package ru.yandex.practicum.filmorate.dal;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.film.Mpa;

import java.util.List;
import java.util.Optional;

@Repository
public class MpaRepository extends BaseRepository<Mpa> {
    private static final String FIND_ALL_QUERY = "SELECT * FROM rating ORDER BY rating_id";
    private static final String FIND_BY_ID_QUERY = "SELECT * FROM rating WHERE rating_id = ?";

    public MpaRepository(JdbcTemplate jdbc, RowMapper<Mpa> mapper) {
        super(jdbc, mapper);
    }

    public List<Mpa> findByAll() {
        return findMany(FIND_ALL_QUERY);
    }

    public Optional<Mpa> findById(int id) {
        return findOne(
                FIND_BY_ID_QUERY,
                id);
    }
}
