package ru.yandex.practicum.filmorate.storage.film;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dal.GenreFilmRepository;
import ru.yandex.practicum.filmorate.model.film.Genre;
import ru.yandex.practicum.filmorate.model.film.GenreFilm;

import java.util.List;


@Component
public class GenreFilmDbStorage {
    private final GenreFilmRepository genreFilmRepository;
    private final GenreDbStorage genreDbStorage;

    public GenreFilmDbStorage(GenreFilmRepository genreFilmRepository, GenreDbStorage genreDbStorage) {
        this.genreFilmRepository = genreFilmRepository;
        this.genreDbStorage = genreDbStorage;
    }

    public List<GenreFilm> findAll() {
        return genreFilmRepository.findAll();
    }

    public List<Genre> findByFilm(int id) {
        List<GenreFilm> genreFilms = genreFilmRepository.findByFilm(id);
        return genreFilms.stream()
                .map(GenreFilm::getIdGenre)
                .map(genreDbStorage::findById)
                .toList();
    }

    public void save(int idFilm, int idGenre) {
        genreFilmRepository.save(idFilm, idGenre);
    }

    public void delete(int idFilm) {
        genreFilmRepository.delete(idFilm);
    }
}
