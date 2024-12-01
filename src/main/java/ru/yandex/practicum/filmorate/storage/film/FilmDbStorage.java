package ru.yandex.practicum.filmorate.storage.film;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dal.FilmRepository;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.film.Film;

import java.util.List;
import java.util.Optional;

@Component
public class FilmDbStorage implements FilmStorage {
    private final FilmRepository filmRepository;
    private final GenreFilmDbStorage genreFilmDbStorage;
    private final FilmLikesDbStorage filmLikesDbStorage;

    public FilmDbStorage(FilmRepository filmRepository, GenreFilmDbStorage genreFilmDbStorage,
                         FilmLikesDbStorage filmLikesDbStorage) {
        this.filmRepository = filmRepository;
        this.genreFilmDbStorage = genreFilmDbStorage;
        this.filmLikesDbStorage = filmLikesDbStorage;
    }


    @Override
    public List<Film> findAll() {
        List<Film> films = filmRepository.findAll();
        films.forEach(film -> {
            film.setGenres(genreFilmDbStorage.findByFilm(film.getId()));
            film.setListLikesUsers(filmLikesDbStorage.findLikesForFilm(film.getId()));
        });
        return films;
    }

    @Override
    public Film create(Film film) {
        return filmRepository.save(film);
    }

    @Override
    public Film update(Film newFilm) {
        return filmRepository.update(newFilm);
    }

    @Override
    public Film findFilmId(int id) {
        Optional<Film> mayBeFilm = filmRepository.findById(id);
        if (mayBeFilm.isEmpty()) {
            throw new NotFoundException("Фильм с id = " + id + " не найден");
        }
        return mayBeFilm.get();
    }
}
