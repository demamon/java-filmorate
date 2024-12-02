package ru.yandex.practicum.filmorate.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.film.Mpa;
import ru.yandex.practicum.filmorate.service.MpaService;

import java.util.Collection;

@RestController
@RequestMapping("/mpa")
public class MpaController {
    private final MpaService mpaService;
    private static final Logger log = LoggerFactory.getLogger(MpaController.class);

    @Autowired
    public MpaController(MpaService mpaService) {
        this.mpaService = mpaService;
    }

    @GetMapping
    public Collection<Mpa> findAll() {
        log.trace("Запросили все имеющиеся mpa");
        return mpaService.findAll();
    }

    @GetMapping("/{id}")
    public Mpa findMpaId(@PathVariable int id) {
        log.trace("Запросили mpa по id {}", id);
        return mpaService.findById(id);
    }
}
