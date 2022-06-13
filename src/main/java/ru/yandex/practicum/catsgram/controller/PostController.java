package ru.yandex.practicum.catsgram.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.catsgram.exception.UserNotFoundException;
import ru.yandex.practicum.catsgram.exception.WrongRequestParamException;
import ru.yandex.practicum.catsgram.model.Post;
import ru.yandex.practicum.catsgram.service.PostService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/posts")
public class PostController {
    private static final Logger log = LoggerFactory.getLogger(PostController.class);
    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping()
    public List<Post> findAll(@RequestParam(name = "size", defaultValue = "10") int size,
                              @RequestParam(name = "sort", defaultValue = "desc") String sort,
                              @RequestParam(name = "from", defaultValue = "1") int from) {
        log.debug("Запрошен список постов. Параметры: size={} sort={} from={}", size, sort, from);
        if (size <= 0 || from <= 0 || (!sort.equals("asc") && !sort.equals("desc"))) {
            throw new WrongRequestParamException("Неверные параметры: " + size + " " + sort + " " + from);
        }
        return postService.findAll(size, sort, from);
    }

    @PostMapping()
    public Post create(@RequestBody Post post) {
        log.debug("Получен пост для создания: {}", post);
        return postService.create(post);
    }

    @GetMapping("/{id}")
    public Optional<Post> getPostById(@PathVariable("id") int postId) {
        log.debug("Запрошен пост: {}", postId);
        return postService.getPostById(postId);
    }
}