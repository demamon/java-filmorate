package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.user.User;
import ru.yandex.practicum.filmorate.service.UserService;

import java.util.Collection;
import java.util.Set;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public Collection<User> findAll() {
        return userService.findAll();
    }

    @PostMapping
    public User create(@Valid @RequestBody User user) {
        log.trace("Передали на добавление пользователя {}", user);
        return userService.create(user);
    }

    @PutMapping
    public User update(@Valid @RequestBody User newUser) {
        log.trace("Передали на обновление пользователя {}", newUser);
        return userService.update(newUser);
    }

    @GetMapping("/{id}")
    public User findUserId(@PathVariable int id) {
        log.trace("Нужно вернуть пользователя с id {}", id);
        return userService.findUserId(id);
    }

    @PutMapping("/{id}/friends/{friendId}")
    public Set<Integer> addFriend(@PathVariable int id, @PathVariable int friendId) {
        log.trace("Пользователь с id {} хочет добавить друга с id {}", id, friendId);
        return userService.addFriend(id, friendId);
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public Set<Integer> deleteFriend(@PathVariable int id, @PathVariable int friendId) {
        log.trace("пользователь с id {} хочет удалить друга с id {}", id, friendId);
        return userService.deleteFriend(id, friendId);
    }

    @GetMapping("/{id}/friends")
    public Collection<User> findFriends(@PathVariable int id) {
        log.trace("Нужно вернуть всех друзей пользователя с id {}", id);
        return userService.findFriends(id);
    }

    @GetMapping("{id}/friends/common/{otherId}")
    public Collection<User> findMutualFriends(@PathVariable int id, @PathVariable int otherId) {
        log.trace("Нужно вернуть общих друзей пользователей c id {} и с id {}", id, otherId);
        return userService.findMutualFriends(id, otherId);
    }

}
