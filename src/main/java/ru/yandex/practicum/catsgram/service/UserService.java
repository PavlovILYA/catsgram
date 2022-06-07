package ru.yandex.practicum.catsgram.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.catsgram.exception.InvalidEmailException;
import ru.yandex.practicum.catsgram.exception.UserAlreadyExistException;
import ru.yandex.practicum.catsgram.model.User;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserService {
    private static final Logger log = LoggerFactory.getLogger(UserService.class);
    private final Set<User> users = new HashSet<>();

    public Set<User> getUsers() {
        log.debug("Текущее количество пользователей {}", users.size());
        return users;
    }

    public User createUser(@RequestBody User user) throws UserAlreadyExistException, InvalidEmailException {
        if (users.contains(user)) {
            throw new UserAlreadyExistException("Пользователь уже существует!");
        }
        return updateUser(user);
    }

    public User updateUser(User user) throws InvalidEmailException {
        if (user.getEmail() == null || user.getEmail().isEmpty()) {
            throw new InvalidEmailException("Email пользователя содержит недопустимое значение!");
        }
        users.add(user);
        log.debug("Добавленный/обновленный пользователь: {}", user);
        return user;
    }

    public int getUsersAmount() {
        return users.size();
    }
}
