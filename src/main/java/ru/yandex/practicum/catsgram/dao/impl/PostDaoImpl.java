package ru.yandex.practicum.catsgram.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.catsgram.dao.PostDao;
import ru.yandex.practicum.catsgram.model.Post;
import ru.yandex.practicum.catsgram.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Collection;

@Component
public class PostDaoImpl implements PostDao {
    private final JdbcTemplate jdbcTemplate;
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    public PostDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Collection<Post> findAllByUser(User user) {
        String sqlQuery = "SELECT * FROM cat_post WHERE author_id = ? ORDER BY creation_date DESC";
        return jdbcTemplate.query(sqlQuery, (resultSet, rowId) -> buildPost(resultSet, user), user.getId());
    }

    private Post buildPost(ResultSet resultSet, User user) throws SQLException {
        return new Post(resultSet.getInt("id"),
                user,
                resultSet.getDate("creation_date").toLocalDate(),
                resultSet.getString("description"),
                resultSet.getString("photo_url"));
    }
}
