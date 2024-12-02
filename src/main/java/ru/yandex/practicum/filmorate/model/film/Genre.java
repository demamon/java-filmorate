package ru.yandex.practicum.filmorate.model.film;

import jakarta.validation.constraints.Max;
import lombok.Data;

@Data
public class Genre {
    @Max(6)
    private int id;
    private String name;
}
