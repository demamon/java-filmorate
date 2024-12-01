package ru.yandex.practicum.filmorate.storage.film;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dal.MpaRepository;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.film.Mpa;

import java.util.List;
import java.util.Optional;

@Component
public class MpaDbStorage {
    private final MpaRepository mpaRepository;

    public MpaDbStorage(MpaRepository mpaRepository) {
        this.mpaRepository = mpaRepository;
    }

    public List<Mpa> findByAll() {
        return mpaRepository.findByAll();
    }

    public Mpa findById(int id) {
        Optional<Mpa> mayBeMpa = mpaRepository.findById(id);
        if (mayBeMpa.isEmpty()) {
            throw new NotFoundException("рейтинга с id = " + id + " не существует");
        }
        return mayBeMpa.get();
    }
}
