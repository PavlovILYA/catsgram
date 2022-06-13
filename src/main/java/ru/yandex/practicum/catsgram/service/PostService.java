package ru.yandex.practicum.catsgram.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import ru.yandex.practicum.catsgram.exception.UserNotFoundException;
import ru.yandex.practicum.catsgram.exception.WrongRequestParamException;
import ru.yandex.practicum.catsgram.model.Post;
import ru.yandex.practicum.catsgram.model.PostFeedParams;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostService {
    private static final Logger log = LoggerFactory.getLogger(PostService.class);
    private final UserService userService;
    private final List<Post> posts = new ArrayList<>();

    @Autowired
    public PostService(UserService userService) {
        this.userService = userService;
    }

    public List<Post> findAll(int size, String sort, int from) {
        Comparator<Post> comparator = Comparator.comparing(Post::getCreationDate).reversed();
        if (sort.equals("asc")) {
            comparator = comparator.reversed();
        }
        return posts.stream()
                .sorted(comparator)
                .skip((long) (from - 1) * size)
                .limit(size)
                .collect(Collectors.toList());
    }

    public Optional<Post> getPostById(int postId) {
        return posts.stream()
                .filter(post -> (post.getId() == postId))
                .findFirst();
    }

    public Post create(Post post) {
        if (userService.findUserByEmail(post.getAuthor()) == null) {
            throw new UserNotFoundException("Пользователь с почтой " + post.getAuthor() + " не найден");
        }
        log.debug("Пост: {}", post);
        posts.add(post);
        return post;
    }

    public List<Post> getNews(PostFeedParams postFeedParams) {
        Comparator<Post> comparator = Comparator.comparing(Post::getCreationDate).reversed();
        if (postFeedParams.getSort().equals("asc")) {
            comparator = comparator.reversed();
        }
        return posts.stream()
                .sorted(comparator)
                .filter(post -> postFeedParams.getFriends().contains(post.getAuthor()))
                .limit(postFeedParams.getSize())
                .collect(Collectors.toList());
    }

    public int getPostsAmount() {
        return posts.size();
    }
}
