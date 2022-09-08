package ru.yandex.practicum.catsgram.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.catsgram.service.HackCatService;

import java.util.Optional;

@RestController
public class HomeController {
    private static final Logger log = LoggerFactory.getLogger(HomeController.class);
    private final HackCatService hackCatService;

    @Autowired
    public HomeController(HackCatService hackCatService) {
        this.hackCatService = hackCatService;
    }

    @GetMapping("/do-hack")
    public Optional<String> doHack() {
        return hackCatService.doHack();
    }

    @GetMapping("/home")
    public String homePage() {
        log.debug("Запрос получен.");
        return "Котограм";
    }
}
