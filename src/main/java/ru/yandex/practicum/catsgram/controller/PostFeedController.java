package ru.yandex.practicum.catsgram.controller;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.catsgram.model.Post;
import ru.yandex.practicum.catsgram.model.PostFeedParams;
import ru.yandex.practicum.catsgram.service.PostService;

import java.util.List;

@RestController
@RequestMapping("/feed/friends")
public class PostFeedController {
    private static final Logger log = LoggerFactory.getLogger(PostFeedController.class);
    private final PostService postService;

    public PostFeedController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping()
    public List<Post> getNews(@RequestBody String json) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
//        mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        log.info(json);
        json = mapper.readValue(json, String.class);
        log.info(json);
        PostFeedParams postFeedParams = mapper.readValue(json, PostFeedParams.class);
        return postService.getNews(postFeedParams);
    }
}
