package ru.yandex.practicum.catsgram.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.catsgram.exception.UserNotFoundException;
import ru.yandex.practicum.catsgram.model.User;
import ru.yandex.practicum.catsgram.service.OldUserService;
import ru.yandex.practicum.catsgram.service.UserService;

import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/users")
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    private final OldUserService oldUserService;
    private final UserService userService;

    @Autowired
    public UserController(OldUserService oldUserService, UserService userService) {
        this.oldUserService = oldUserService;
        this.userService = userService;
    }

    @GetMapping()
    public Set<User> getUsers() {
        log.debug("Запрошен список пользователей. Количество: {}", oldUserService.getUsersAmount());
        return oldUserService.getUsers();
    }

    @PostMapping()
    public User createUser(@RequestBody User user) {
        log.debug("Получен пользователь для создания: {}", user);
        return oldUserService.createUser(user);
    }

    @PutMapping()
    public User updateUser(@RequestBody User user) {
        log.debug("Получен пользователь для обновления: {}", user);
        return oldUserService.updateUser(user);
    }

    @GetMapping("/{id:.+}")
    public User findUserById(@PathVariable("id") String userID) {
        log.debug("Запрошен пользователь: {}", userID);
        Optional<User> user = userService.findUserById(userID);
        if (user.isEmpty()) {
            throw new UserNotFoundException("Не найден((");
        }
        return user.get();
    }
}
