package ru.yandex.practicum.filmorate.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.user.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.*;

@Service
public class UserService {
    private final UserStorage userStorage;
    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    @Autowired
    public UserService(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public Collection<User> findAll() {
        return userStorage.findAll();
    }

    public User create(User user) {
        return userStorage.create(user);
    }

    public User update(User newUser) {
        return userStorage.update(newUser);
    }

    public User findUserId(int id) {
        return userStorage.findUserId(id);
    }

    public Set<Integer> addFriend(int userId, int friendId) {
        return userStorage.addFriend(userId, friendId);
    }

    public Set<Integer> deleteFriend(int userId, int friendId) {
        return userStorage.deleteFriend(userId, friendId);
    }

    public Collection<User> findFriends(int id) {
        return userStorage.findFriends(id);
    }

    public Collection<User> findMutualFriends(int userId, int userOtherId) {
        return userStorage.findMutualFriends(userId, userOtherId);
    }
}
