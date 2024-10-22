package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/films")
public class FilmController {
    private final FilmService filmService;
    private static final Logger log = LoggerFactory.getLogger(FilmController.class);

    @Autowired
    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    @GetMapping
    public Collection<Film> findAll() {
        return filmService.findAll();
    }

    @PostMapping
    public Film create(@Valid @RequestBody Film film) {
        log.trace("Передали на добавление фильм {}", film);
        return filmService.create(film);
    }

    @PutMapping
    public Film update(@Valid @RequestBody Film newFilm) {
        log.trace("Нужно обновить фильм {}", newFilm);
        return filmService.update(newFilm);
    }

    @GetMapping("/{id}")
    public Film findFilmId(@PathVariable int id) {
        log.trace("Нужно вернуть фильм с id {}", id);
        return filmService.findFilmId(id);
    }

    @PutMapping("/{id}/like/{userId}")
    public Set<Integer> addLikeFilm(@PathVariable int id, @PathVariable int userId) {
        log.trace("Пользователь с id {} хочет поставить лайк фильму с id {}", userId, id);
        return filmService.addLikeFilm(userId, id);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public Set<Integer> deleteLikeFilm(@PathVariable int id, @PathVariable int userId) {
        log.trace("Пользователь с id {} хочет убрать лайк с фильма с id {}", userId, id);
        return filmService.deleteLikeFilm(userId, id);
    }

    @GetMapping("/popular")
    public Film[] findMorePopularFilm(@RequestParam Optional<Integer> count) {
        log.trace("Нужно вернуть фильмы с наибольшим количеством лайков, количество фильмов указано в count");
        int usedCount = 10;
        if (count.isPresent() && count.get() > 0) {
            usedCount = count.get();
        }
        log.debug("Нужно вернуть {} наиболее популярных фильмов", usedCount);
        return filmService.findMorePopularFilm(usedCount);
    }
}
