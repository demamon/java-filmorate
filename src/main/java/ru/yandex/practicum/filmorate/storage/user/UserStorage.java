package ru.yandex.practicum.filmorate.storage.user;

import ru.yandex.practicum.filmorate.model.user.User;

import java.util.Collection;
import java.util.Set;

public interface UserStorage {

    Collection<User> findAll();

    User create(User user);

    User update(User newUser);

    User findUserId(int id);

    Collection<User> findFriends(int id);

    Set<Integer> addFriend(int userId, int friendId);

    Set<Integer> deleteFriend(int userId, int friendId);

    Collection<User> findMutualFriends(int userId, int userOtherId);
}
