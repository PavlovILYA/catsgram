package ru.yandex.practicum.catsgram.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.catsgram.dao.UserDao;
import ru.yandex.practicum.catsgram.model.User;

import java.util.Optional;

@Component
public class UserDaoImpl implements UserDao {
    private final JdbcTemplate jdbcTemplate;
    private final Logger log = LoggerFactory.getLogger(UserDaoImpl.class);

    @Autowired
    public UserDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<User> findUserById(String id) {
        SqlRowSet userRows = jdbcTemplate.queryForRowSet("SELECT * FROM cat_user WHERE id = ?", id);

        if (userRows.next()) {
            User user = new User(userRows.getString("id"),
                    userRows.getString("nickname"),
                    userRows.getString("username"));
            log.info("найден пользователь: {} {} {}",
                    user.getId(),
                    user.getNickname(),
                    user.getUsername());
            return Optional.of(user);
        } else {
            log.info("Пользователя с id = {} нет", id);
            return Optional.empty();
        }

    }
}
