package ru.yandex.practicum.filmorate.storage.film;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dal.GenreRepository;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.film.Genre;

import java.util.List;
import java.util.Optional;

@Component
public class GenreDbStorage {
    private final GenreRepository genreRepository;
    private static final Logger log = LoggerFactory.getLogger(GenreDbStorage.class);

    public GenreDbStorage(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    public List<Genre> findAll() {
        return genreRepository.findAll();
    }

    public Genre findById(int id) {
        Optional<Genre> mayBeGenre = genreRepository.findById(id);
        if (mayBeGenre.isEmpty()) {
            log.error("Жанра с id =  {} не существует", id);
            throw new NotFoundException("Жанра с id = " + id + " не существует");
        }
        return mayBeGenre.get();
    }
}
