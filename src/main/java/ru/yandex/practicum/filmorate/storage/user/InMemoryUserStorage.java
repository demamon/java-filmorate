package ru.yandex.practicum.filmorate.storage.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Component
public class InMemoryUserStorage implements UserStorage {

    private final Map<Integer, User> users = new HashMap<>();

    private static final Logger log = LoggerFactory.getLogger(InMemoryUserStorage.class);

    @Override
    public Collection<User> findAll() {
        return users.values();
    }

    @Override
    public User create(User user) {
        user.setId(getNextId());
        validationNameUser(user);
        log.debug("добавляем нового пользователя {}", user);
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public User update(User newUser) {
        if (users.containsKey(newUser.getId())) {
            validationNameUser(newUser);
            log.debug("добавляем данные текущего пользователя {}", newUser);
            users.put(newUser.getId(), newUser);
            return newUser;
        }
        log.error("Пользователь с id = {} не найден", newUser.getId());
        throw new NotFoundException("Пользователь с id = " + newUser.getId() + " не найден");
    }

    @Override
    public User findUserId(int id) {
        User user = users.get(id);
        if (user == null) {
            log.error("Пользователь с id = " + id + " не найден");
            throw new NotFoundException("Пользователь с id = " + id + " не найден");
        }
        log.debug("Возвращаем запрашиваемого пользователя {}, с id {}", user, id);
        return user;
    }

    private void validationNameUser(User user) {
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
    }

    private int getNextId() {
        int currentMaxId = users.keySet()
                .stream()
                .mapToInt(id -> id)
                .max()
                .orElse(0);
        return ++currentMaxId;
    }
}
