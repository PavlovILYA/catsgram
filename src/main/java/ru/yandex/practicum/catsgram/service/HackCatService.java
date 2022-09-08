package ru.yandex.practicum.catsgram.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class HackCatService {
    private final Logger log = LoggerFactory.getLogger(HackCatService.class);
    public static final String JDBC_URL="jdbc:postgresql://127.0.0.1:5432/cats";
    public static final String JDBC_USERNAME="kitty";
    public static final String JDBC_DRIVER="org.postgresql.Driver";

    public Optional<String> doHack() {
        List<String> catWordList = Arrays.asList("purr", "purrrrrr", "purrrrr");
        for (String attempt : catWordList) {
            try {
                tryPassword(attempt);
                return Optional.of(attempt);
            } catch (Exception e) {
                log.info("Пароль не подошел ({})", attempt);
            }
        }
        return Optional.empty();
    }

    private void tryPassword(String attempt) {
        log.info("Пробуем пароль: {}", attempt);
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(JDBC_DRIVER);
        dataSource.setUrl(JDBC_URL);
        dataSource.setUsername(JDBC_USERNAME);
        dataSource.setPassword(attempt);
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        log.info("db response: {}", jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM cat_user", Integer.class));
    }
}
