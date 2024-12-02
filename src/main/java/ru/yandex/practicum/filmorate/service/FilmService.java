package ru.yandex.practicum.filmorate.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.film.Film;
import ru.yandex.practicum.filmorate.model.film.Genre;
import ru.yandex.practicum.filmorate.model.user.User;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.film.GenreFilmDbStorage;
import ru.yandex.practicum.filmorate.storage.film.FilmLikesDbStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.*;

import static java.util.Comparator.comparingInt;

@Service
public class FilmService {
    private final FilmStorage filmStorage;
    private final UserStorage userStorage;
    private final GenreFilmDbStorage genreFilmDbStorage;
    private final FilmLikesDbStorage filmLikesDbStorage;
    private static final Logger log = LoggerFactory.getLogger(FilmService.class);

    @Autowired
    public FilmService(FilmStorage filmStorage, UserStorage userStorage, GenreFilmDbStorage genreFilmDbStorage,
                       FilmLikesDbStorage filmLikesDbStorage) {
        this.filmStorage = filmStorage;
        this.userStorage = userStorage;
        this.genreFilmDbStorage = genreFilmDbStorage;
        this.filmLikesDbStorage = filmLikesDbStorage;
    }

    public Collection<Film> findAll() {
        return filmStorage.findAll();
    }

    public Film create(Film film) {
        film = filmStorage.create(film);
        List<Genre> genreList = film.getGenres().stream().distinct().toList();
        int filmId = film.getId();
        genreList.forEach(genre -> genreFilmDbStorage.save(filmId, genre.getId()));
        return film;
    }

    public Film update(Film newFilm) {
        filmStorage.findFilmId(newFilm.getId());
        int filmId = newFilm.getId();
        genreFilmDbStorage.delete(filmId);
        List<Genre> genreList = newFilm.getGenres().stream().distinct().toList();
        genreList.forEach(genre -> genreFilmDbStorage.save(filmId, genre.getId()));
        return filmStorage.update(newFilm);
    }

    public Film findFilmId(int id) {
        Film film = filmStorage.findFilmId(id);
        film.setGenres(genreFilmDbStorage.findByFilm(id));
        return film;
    }

    public Set<Integer> addLikeFilm(int userId, int filmId) {
        Film film = filmStorage.findFilmId(filmId);
        log.debug("Фильм которому нужно поставить лайк {}", film);
        User user = userStorage.findUserId(userId);
        log.debug("Пользователь который ставить лайк {}", user);
        Set<Integer> listLikesUsersFilm = filmLikesDbStorage.findLikesForFilm(filmId);
        boolean isLike = listLikesUsersFilm.contains(userId);
        if (isLike) {
            log.error("Пользователь с логином {} уже поставил лайк фильму {}", user.getLogin(), film.getName());
            throw new ValidationException("Пользователь с логином " + user.getLogin() + " уже поставил лайк фильму " +
                    film.getName());
        }
        listLikesUsersFilm.add(userId);
        film.setListLikesUsers(listLikesUsersFilm);
        filmLikesDbStorage.saveLike(filmId, userId);
        log.debug("Пользователь с логином {} успешно поставил лайк фильму {}", user.getLogin(), film.getName());
        log.debug("Список id пользователей поставивших лайки фильму {}", film.getListLikesUsers());
        return listLikesUsersFilm;
    }

    public Set<Integer> deleteLikeFilm(int userId, int filmId) {
        Film film = filmStorage.findFilmId(filmId);
        log.debug("Фильм с которого нужно убрать лайк {}", film);
        User user = userStorage.findUserId(userId);
        log.debug("Пользователь который убирает лайк {}", user);
        Set<Integer> listLikesUsersFilm = filmLikesDbStorage.findLikesForFilm(filmId);
        boolean isNotLike = !(listLikesUsersFilm.contains(userId));
        if (isNotLike) {
            log.error("Пользователь с логином {} не ставил лайк фильму {}", user.getLogin(), film.getName());
            throw new ValidationException("Пользователь с логином " + user.getLogin() + " не ставил лайк фильму " +
                    film.getName());
        }
        listLikesUsersFilm.remove(userId);
        film.setListLikesUsers(listLikesUsersFilm);
        filmLikesDbStorage.deleteLike(filmId, userId);
        log.debug("Пользователь с логином {} успешно убрал лайк с фильма {}", user.getLogin(), film.getName());
        log.debug("Список id пользователей поставивших лайки фильму {}", film.getListLikesUsers());
        return listLikesUsersFilm;
    }

    public Film[] findMorePopularFilm(int count) {
        Collection<Film> allFilm = filmStorage.findAll();
        log.debug("Список всех фильмов {}", allFilm);
        Film[] films = allFilm.stream()
                .filter(film -> !film.getListLikesUsers().isEmpty())
                .sorted(comparingInt(film -> (film.getListLikesUsers().size())))
                .limit(count)
                .toArray(Film[]::new);
        Arrays.sort(films);
        return films;
    }
}
