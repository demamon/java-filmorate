package ru.yandex.practicum.filmorate.dal;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.user.User;

import java.util.List;
import java.util.Optional;

@Repository
public class UserRepository extends BaseRepository<User> {
    private static final String FIND_ALL_QUERY = "SELECT * FROM users";
    private static final String FIND_BY_ID_QUERY = "SELECT * FROM users WHERE id = ?";
    private static final String INSERT_QUERY = "INSERT INTO users(email, login, name, birthday)" +
            "VALUES (?, ?, ?, ?)";
    private static final String UPDATE_QUERY = "UPDATE users SET email = ?, login = ?, name = ?, birthday = ?" +
            "WHERE id = ?";
    private static final String FIND_ALL_FRIEND_QUERY = "SELECT * FROM users WHERE id IN (SELECT user_id2 FROM friends " +
            "WHERE user_id1 = ?)";
    private static final String INSERT_NEW_FRIEND_QUERY = "INSERT INTO friends(user_id1, user_id2) VALUES (?, ?)";
    private static final String DELETE_FRIEND_QUERY = "DELETE FROM friends WHERE user_id1 = ? AND user_id2 = ?";

    public UserRepository(JdbcTemplate jdbc, RowMapper<User> mapper) {
        super(jdbc, mapper);
    }

    public List<User> findAll() {
        return findMany(FIND_ALL_QUERY);
    }

    public Optional<User> findById(int id) {
        return findOne(FIND_BY_ID_QUERY, id);
    }

    public User save(User user) {
        int id = insert(
                INSERT_QUERY,
                user.getEmail(),
                user.getLogin(),
                user.getName(),
                user.getBirthday()
        );
        user.setId(id);
        return user;
    }

    public User update(User user) {
        update(
                UPDATE_QUERY,
                user.getEmail(),
                user.getLogin(),
                user.getName(),
                user.getBirthday(),
                user.getId()
        );
        return user;
    }

    public List<User> findFriends(int id) {
        return findMany(FIND_ALL_FRIEND_QUERY, id);
    }

    public void addFriend(int userId, int friendId) {
        update(
                INSERT_NEW_FRIEND_QUERY,
                userId,
                friendId
        );
    }

    public void deleteFriend(int userId, int friendId) {
        update(
                DELETE_FRIEND_QUERY,
                userId,
                friendId
        );
    }
}
