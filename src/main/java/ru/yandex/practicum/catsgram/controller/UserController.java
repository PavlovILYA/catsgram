package ru.yandex.practicum.catsgram.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.catsgram.exception.InvalidEmailException;
import ru.yandex.practicum.catsgram.exception.UserAlreadyExistException;
import ru.yandex.practicum.catsgram.model.User;
import ru.yandex.practicum.catsgram.service.UserService;

import java.util.Set;

@RestController
@RequestMapping("/users")
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public Set<User> getUsers() {
        log.debug("Запрошен список пользователей. Количество: {}", userService.getUsersAmount());
        return userService.getUsers();
    }

    @PostMapping()
    public User createUser(@RequestBody User user) {
        log.debug("Получен пользователь для создания: {}", user);
        return userService.createUser(user);
    }

    @PutMapping()
    public User updateUser(@RequestBody User user) {
        log.debug("Получен пользователь для обновления: {}", user);
        return userService.updateUser(user);
    }
}
