package ru.yandex.practicum.catsgram.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.catsgram.exception.UserNotFoundException;
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
    public List<Post> findAll() {
        log.debug("Запрошен список постов. Количество: {}", postService.getPostsAmount());
        return postService.findAll();
    }

    @PostMapping()
    public Post create(@RequestBody Post post) {
        log.debug("Получен пост для создания: {}", post);
        return postService.create(post);
    }

    @GetMapping("/{id}")
    public Optional<Post> getPostById(@PathVariable("id") int postId) {
        log.debug("Запрошен пост: {}", postId);
        return postService.findAll()
                .stream()
                .filter(post -> (post.getId() == postId))
                .findFirst();
    }
}