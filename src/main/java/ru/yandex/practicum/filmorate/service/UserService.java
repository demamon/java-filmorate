package ru.yandex.practicum.filmorate.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.*;
import java.util.stream.Collectors;

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
        User user = userStorage.findUserId(userId);
        log.debug("Пользователь который добавляет в друзья {}", user);
        User friend = userStorage.findUserId(friendId);
        log.debug("Пользователь которого добавляют в друзья {}", friend);
        Set<Integer> userFriendsList = user.getFriendsList();
        Set<Integer> friendFriendsList = friend.getFriendsList();
        boolean isFriend = userFriendsList.contains(friendId);
        if (isFriend) {
            log.error("{} и {} уже друзья", user.getName(), friend.getName());
            throw new ValidationException(user.getName() + " и " + friend.getName() + " уже друзья");
        }
        userFriendsList.add(friendId);
        friendFriendsList.add(userId);
        user.setFriendsList(userFriendsList);
        friend.setFriendsList(friendFriendsList);
        log.debug("{} и {} теперь друзья", user.getName(), friend.getName());
        log.debug("Список друзей {} пользователя {}", user.getFriendsList(), user.getName());
        log.debug("Список друзей {} пользователя {}", friend.getFriendsList(), friend.getName());
        return userFriendsList;
    }

    public Set<Integer> deleteFriend(int userId, int friendId) {
        User user = userStorage.findUserId(userId);
        log.debug("Пользователь который удаляет из друзей {}", user);
        User friend = userStorage.findUserId(friendId);
        log.debug("Пользователь которого удаляют из друзей {}", friend);
        Set<Integer> userFriendsList = user.getFriendsList();
        Set<Integer> friendFriendsList = friend.getFriendsList();
        userFriendsList.remove(friendId);
        friendFriendsList.remove(userId);
        user.setFriendsList(userFriendsList);
        friend.setFriendsList(friendFriendsList);
        log.debug("{} и {} больше не друзья", user.getName(), friend.getName());
        log.debug("Список друзей {} пользователя {}", user.getFriendsList(), user.getName());
        log.debug("Список друзей {} пользователя {}", friend.getFriendsList(), friend.getName());
        return userFriendsList;
    }

    public Collection<User> findFriends(int id) {
        User user = userStorage.findUserId(id);
        log.debug("Возвращаем список всех друзей пользователя {}", user);
        return user.getFriendsList().stream()
                .map(userStorage::findUserId)
                .collect(Collectors.toSet());
    }

    public Collection<User> findMutualFriends(int userId, int userOtherId) {
        User user = userStorage.findUserId(userId);
        User userOther = userStorage.findUserId(userOtherId);
        Set<Integer> userFriendsList = user.getFriendsList();
        log.debug("Список друзей 1 пользователя {}", userFriendsList);
        Set<Integer> userOtherFriendsList = userOther.getFriendsList();
        log.debug("Список друзей 2 пользователя {}", userOtherFriendsList);
        return userFriendsList.stream()
                .filter(userOtherFriendsList::contains)
                .map(userStorage::findUserId)
                .collect(Collectors.toSet());
    }
}
