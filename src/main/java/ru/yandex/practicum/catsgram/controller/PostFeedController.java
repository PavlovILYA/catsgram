package ru.yandex.practicum.catsgram.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.catsgram.exception.IncorrectParameterException;
import ru.yandex.practicum.catsgram.model.Post;
import ru.yandex.practicum.catsgram.model.PostFeedParams;
import ru.yandex.practicum.catsgram.service.PostService;

import java.util.List;

@RestController
@RequestMapping("/feed")
public class PostFeedController {
    private static final Logger log = LoggerFactory.getLogger(PostFeedController.class);
    private final PostService postService;

    public PostFeedController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping(value = "/friends")
    public List<Post> getNews(@RequestBody String json) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
//        mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        log.info(json);
        json = mapper.readValue(json, String.class);
        log.info(json);
        PostFeedParams postFeedParams = mapper.readValue(json, PostFeedParams.class);

        if (postFeedParams.getSize() == null || postFeedParams.getSize() <= 0) {
            throw new IncorrectParameterException("size", "Недопустимое значение");
        } else if (!postFeedParams.getSort().equals("asc") && !postFeedParams.getSort().equals("desc")) {
            throw new IncorrectParameterException("sort", "Недопустимое значение");
        }

        return postService.getNews(postFeedParams);
    }

    @GetMapping(value = "/follow")
    public List<Post> getFollowFeed(@RequestParam(name = "userId") String userId,
                                    @RequestParam(name = "size") int size) {
        return postService.getFollowFeed(userId, size);
    }

    @GetMapping(value = "/follow2")
    public List<Post> getFollowFeed2(@RequestParam(name = "userId") String userId,
                                    @RequestParam(name = "size") int size) {
        return postService.getFollowFeed2(userId, size);
    }
}
