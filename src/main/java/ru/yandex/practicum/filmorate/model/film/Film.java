package ru.yandex.practicum.filmorate.model.film;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;
import ru.yandex.practicum.filmorate.validator.date.MinimumDate;

import java.time.LocalDate;
import java.util.*;

@Data
public class Film implements Comparable<Film> {
    private int id;
    @NotBlank
    private String name;
    @Size(max = 200)
    private String description;
    @MinimumDate
    private LocalDate releaseDate;
    @Positive
    private int duration;
    private Set<Integer> listLikesUsers;
    private List<@Valid Genre> genres;
    @Valid
    private Mpa mpa;

    public Film(String name, String description, LocalDate releaseDate, int duration) {
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.duration = duration;
    }

    public Film() {
    }

    public Set<Integer> getListLikesUsers() {
        if (listLikesUsers == null) {
            return new HashSet<>();
        }
        return listLikesUsers;
    }

    public List<Genre> getGenres() {
        if (genres == null) {
            return new ArrayList<>();
        }
        return genres;
    }

    @Override
    public int compareTo(Film film) {
        return film.getListLikesUsers().size() - this.getListLikesUsers().size();
    }
}
