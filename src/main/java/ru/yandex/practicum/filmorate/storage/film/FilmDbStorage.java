package ru.yandex.practicum.filmorate.storage.film;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dal.FilmRepository;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.film.Film;
import ru.yandex.practicum.filmorate.model.film.Genre;

import java.util.*;

@Component
public class FilmDbStorage implements FilmStorage {
    private final FilmRepository filmRepository;
    private final GenreFilmDbStorage genreFilmDbStorage;
    private final FilmLikesDbStorage filmLikesDbStorage;
    private final GenreDbStorage genreDbStorage;

    public FilmDbStorage(FilmRepository filmRepository, GenreFilmDbStorage genreFilmDbStorage,
                         FilmLikesDbStorage filmLikesDbStorage, GenreDbStorage genreDbStorage) {
        this.filmRepository = filmRepository;
        this.genreFilmDbStorage = genreFilmDbStorage;
        this.filmLikesDbStorage = filmLikesDbStorage;
        this.genreDbStorage = genreDbStorage;
    }


    @Override
    public List<Film> findAll() {
        List<Film> films = filmRepository.findAll();
        Map<Integer, List<Genre>> genreFilmMap = new HashMap<>();
        genreFilmDbStorage.findAll().forEach(genreFilm -> {
            if (genreFilmMap.containsKey(genreFilm.getIdFilm())) {
                genreFilmMap.get(genreFilm.getIdFilm()).add(genreDbStorage.findById(genreFilm.getIdGenre()));
            } else {
                List<Genre> genres = new ArrayList<>();
                genres.add(genreDbStorage.findById(genreFilm.getIdGenre()));
                genreFilmMap.put(genreFilm.getIdFilm(), genres);
            }
        });
        Map<Integer, Set<Integer>> filmLikesMap = new HashMap<>();
        filmLikesDbStorage.findAll().forEach(filmLikes -> {
            if (filmLikesMap.containsKey(filmLikes.getIdFilm())) {
                filmLikesMap.get(filmLikes.getIdFilm()).add(filmLikes.getIdUser());
            } else {
                Set<Integer> likes = new HashSet<>();
                likes.add(filmLikes.getIdUser());
                filmLikesMap.put(filmLikes.getIdFilm(), likes);
            }
        });
        films.forEach(film -> {
            film.setGenres(genreFilmMap.get(film.getId()));
            film.setListLikesUsers(filmLikesMap.get(film.getId()));
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
        Film film = mayBeFilm.get();
        film.setGenres(genreFilmDbStorage.findByFilm(film.getId()));
        film.setListLikesUsers(filmLikesDbStorage.findLikesForFilm(film.getId()));
        return film;
    }
}
