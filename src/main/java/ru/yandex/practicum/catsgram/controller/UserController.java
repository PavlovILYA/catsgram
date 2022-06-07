package ru.yandex.practicum.catsgram.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.catsgram.exception.InvalidEmailException;
import ru.yandex.practicum.catsgram.exception.UserAlreadyExistException;
import ru.yandex.practicum.catsgram.model.User;
import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/users")
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    private final Set<User> users = new HashSet<>();

    @GetMapping()
    public Set<User> getUsers() {
        log.debug("Текущее количество пользователей {}", users.size());
        return users;
    }

    @PostMapping()
    public User createUser(@RequestBody User user) throws UserAlreadyExistException, InvalidEmailException {
        if (users.contains(user)) {
            throw new UserAlreadyExistException("Пользователь уже существует!");
        }
        return updateUser(user);
    }

    @PutMapping()
    public User updateUser(@RequestBody User user) throws InvalidEmailException {
        if (user.getEmail() == null || user.getEmail().isEmpty()) {
            throw new InvalidEmailException("Email пользователя содержит недопустимое значение!");
        }
        users.add(user);
        log.debug("Добавленный/обновленный пользователь: {}", user);
        return user;
    }
}
