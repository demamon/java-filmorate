package ru.yandex.practicum.filmorate.model.film;

import jakarta.validation.constraints.Max;
import lombok.Data;

@Data
public class Mpa {
    @Max(5)
    private int id;
    private String name;
}
