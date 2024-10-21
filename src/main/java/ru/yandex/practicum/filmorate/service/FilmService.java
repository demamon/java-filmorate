package ru.yandex.practicum.filmorate.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.*;

import static java.util.Comparator.comparingInt;

@Service
public class FilmService {
    private final FilmStorage filmStorage;
    private final UserStorage userStorage;
    private static final Logger log = LoggerFactory.getLogger(FilmService.class);

    @Autowired
    public FilmService(FilmStorage filmStorage, UserStorage userStorage) {
        this.filmStorage = filmStorage;
        this.userStorage = userStorage;
    }

    public Collection<Film> findAll() {
        return filmStorage.findAll();
    }

    public Film create(Film film) {
        return filmStorage.create(film);
    }

    public Film update(Film newFilm) {
        return filmStorage.update(newFilm);
    }

    public Film findFilmId(int id) {
        return filmStorage.findFilmId(id);
    }

    public Set<Integer> addLikeFilm(int userId, int filmId) {
        Film film = filmStorage.findFilmId(filmId);
        log.debug("Фильм которому нужно поставить лайк {}", film);
        User user = userStorage.findUserId(userId);
        log.debug("Пользователь который ставить лайк {}", user);
        Set<Integer> listLikesUsersFilm = film.getListLikesUsers();
        boolean isLike = listLikesUsersFilm.contains(userId);
        if (isLike) {
            log.error("Пользователь с логином {} уже поставил лайк фильму {}", user.getLogin(), film.getName());
            throw new ValidationException("Пользователь с логином " + user.getLogin() + " уже поставил лайк фильму " +
                    film.getName());
        }
        listLikesUsersFilm.add(userId);
        film.setListLikesUsers(listLikesUsersFilm);
        log.debug("Пользователь с логином {} успешно поставил лайк фильму {}", user.getLogin(), film.getName());
        log.debug("Список id пользователей поставивших лайки фильму {}", film.getListLikesUsers());
        return listLikesUsersFilm;
    }

    public Set<Integer> deleteLikeFilm(int userId, int filmId) {
        Film film = filmStorage.findFilmId(filmId);
        log.debug("Фильм с которого нужно убрать лайк {}", film);
        User user = userStorage.findUserId(userId);
        log.debug("Пользователь который убирает лайк {}", user);
        Set<Integer> listLikesUsersFilm = film.getListLikesUsers();
        boolean isNotLike = !(listLikesUsersFilm.contains(userId));
        if (isNotLike) {
            log.error("Пользователь с логином {} не ставил лайк фильму {}", user.getLogin(), film.getName());
            throw new ValidationException("Пользователь с логином " + user.getLogin() + " не ставил лайк фильму " +
                    film.getName());
        }
        listLikesUsersFilm.remove(userId);
        film.setListLikesUsers(listLikesUsersFilm);
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
