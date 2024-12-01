package ru.yandex.practicum.filmorate.storage.user;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dal.UserRepository;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.user.User;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class UserDbStorage implements UserStorage {
    private final UserRepository userRepository;

    public UserDbStorage(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Collection<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User create(User user) {
        validationNameUser(user);
        return userRepository.save(user);
    }

    @Override
    public User update(User newUser) {
        Optional<User> mayBeUser = userRepository.findById(newUser.getId());
        if (mayBeUser.isPresent()) {
            validationNameUser(newUser);
            return userRepository.update(newUser);
        }
        throw new NotFoundException("Пользователь с id = " + newUser.getId() + " не найден");
    }

    @Override
    public User findUserId(int id) {
        Optional<User> mayBeUser = userRepository.findById(id);
        if (mayBeUser.isPresent()) {
            return mayBeUser.get();
        }
        throw new NotFoundException("Пользователь с id = " + id + " не найден");
    }

    @Override
    public Collection<User> findFriends(int id) {
        Optional<User> mayBeUser = userRepository.findById(id);
        if (mayBeUser.isPresent()) {
            return userRepository.findFriends(id);
        }
        throw new NotFoundException("Пользователь с id = " + id + " не найден");
    }

    @Override
    public Set<Integer> addFriend(int userId, int friendId) {
        Optional<User> mayBeUser = userRepository.findById(userId);
        if (mayBeUser.isEmpty()) {
            throw new NotFoundException("Пользователь с id = " + userId + " не найден");
        }
        mayBeUser = userRepository.findById(friendId);
        if (mayBeUser.isEmpty()) {
            throw new NotFoundException("Пользователь с id = " + friendId + " не найден");
        }
        userRepository.addFriend(userId, friendId);
        return userRepository.findFriends(userId).stream()
                .map(User::getId)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<Integer> deleteFriend(int userId, int friendId) {
        Optional<User> mayBeUser = userRepository.findById(userId);
        if (mayBeUser.isEmpty()) {
            throw new NotFoundException("Пользователь с id = " + userId + " не найден");
        }
        Optional<User> mayBeFriend = userRepository.findById(friendId);
        if (mayBeFriend.isEmpty()) {
            throw new NotFoundException("Пользователь с id = " + friendId + " не найден");
        }
        userRepository.deleteFriend(userId, friendId);
        return userRepository.findFriends(userId).stream()
                .map(User::getId)
                .collect(Collectors.toSet());
    }

    @Override
    public Collection<User> findMutualFriends(int userId, int userOtherId) {
        Optional<User> mayBeUser = userRepository.findById(userId);
        if (mayBeUser.isEmpty()) {
            throw new NotFoundException("Пользователь с id = " + userId + " не найден");
        }
        Optional<User> mayBeFriend = userRepository.findById(userOtherId);
        if (mayBeFriend.isEmpty()) {
            throw new NotFoundException("Пользователь с id = " + userOtherId + " не найден");
        }
        List<User> userFriendList = userRepository.findFriends(userId);
        List<User> userOtherFriendList = userRepository.findFriends(userOtherId);
        return userFriendList.stream()
                .filter(userOtherFriendList::contains)
                .collect(Collectors.toSet());
    }

    private void validationNameUser(User user) {
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
    }
}
