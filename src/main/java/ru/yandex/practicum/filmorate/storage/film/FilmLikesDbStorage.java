package ru.yandex.practicum.filmorate.storage.film;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dal.FilmLikesRepository;
import ru.yandex.practicum.filmorate.model.film.FilmLikes;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class FilmLikesDbStorage {
    private final FilmLikesRepository filmLikesRepository;

    public FilmLikesDbStorage(FilmLikesRepository filmLikesRepository) {
        this.filmLikesRepository = filmLikesRepository;
    }

    public Set<Integer> findLikesForFilm(int idFilm) {
        List<FilmLikes> filmLikes = filmLikesRepository.findByFilm(idFilm);
        return filmLikes.stream()
                .map(FilmLikes::getIdUser)
                .collect(Collectors.toSet());
    }

    public void saveLike(int idFilm, int idUser) {
        filmLikesRepository.save(idFilm, idUser);
    }

    public void deleteLike(int idFilm, int idUser) {
        filmLikesRepository.delete(idFilm, idUser);
    }

    public List<FilmLikes> findAll() {
        return filmLikesRepository.findAll();
    }
}
