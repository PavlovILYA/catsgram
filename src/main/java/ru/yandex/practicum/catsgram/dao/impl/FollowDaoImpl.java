package ru.yandex.practicum.catsgram.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.catsgram.dao.FollowDao;
import ru.yandex.practicum.catsgram.dao.PostDao;
import ru.yandex.practicum.catsgram.dao.UserDao;
import ru.yandex.practicum.catsgram.exception.UserNotFoundException;
import ru.yandex.practicum.catsgram.model.Follow;
import ru.yandex.practicum.catsgram.model.Post;
import ru.yandex.practicum.catsgram.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class FollowDaoImpl implements FollowDao {
    private final JdbcTemplate jdbcTemplate;
    private final UserDao userDao;
    private final PostDao postDao;

    @Autowired
    public FollowDaoImpl(JdbcTemplate jdbcTemplate, UserDao userDao, PostDao postDao) {
        this.jdbcTemplate = jdbcTemplate;
        this.userDao = userDao;
        this.postDao = postDao;
    }

    @Override
    public List<Post> getFollowFeed(String userId, int size) {
        Optional<User> follower = userDao.findUserById(userId);
        if (follower.isEmpty()) {
            return new ArrayList<>();
        }
        String sqlQuery = "SELECT * \n" +
                "  FROM cat_follow \n" +
                "  WHERE follower_id = ?";
        List<Follow> follows = jdbcTemplate.query(sqlQuery, (resultSet, rowId) -> buildFollow(resultSet), userId);
        return follows.stream()
                .flatMap(follow -> postDao.findAllByUser(follow.getAuthor()).stream())
                .sorted(Comparator.comparing(Post::getCreationDate).reversed())
                .limit(size)
                .collect(Collectors.toList());
    }

    @Override
    public List<Post> getFollowFeed2(String userId, int size) {
        String sqlQuery = "SELECT u2.id, u2.username, u2.nickname \n" +
                "  FROM cat_user AS u\n" +
                "  JOIN cat_follow AS f ON u.id = f.follower_id \n" +
                "  JOIN cat_user AS u2 ON f.author_id = u2.id \n" +
                "  WHERE u.id = ?";
        List<User> authors = jdbcTemplate.query(sqlQuery, (resultSet, rowId) -> buildUser(resultSet), userId);
        return authors.stream()
                .flatMap(user -> postDao.findAllByUser(user).stream())
                .sorted(Comparator.comparing(Post::getCreationDate).reversed())
                .limit(size)
                .collect(Collectors.toList());
    }

    private User buildUser(ResultSet resultSet) throws SQLException {
        return new User(resultSet.getString("id"),
                resultSet.getString("username"),
                resultSet.getString("nickname"));
    }

    private Follow buildFollow(ResultSet resultSet) throws SQLException {
        return new Follow(resultSet.getInt("id"),
                userDao.findUserById(
                        resultSet.getString("author_id")).orElseThrow(UserNotFoundException::new),
                userDao.findUserById(
                        resultSet.getString("follower_id")).orElseThrow(UserNotFoundException::new));
    }
}
