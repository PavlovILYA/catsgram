package ru.yandex.practicum.catsgram.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.catsgram.dao.FollowDao;
import ru.yandex.practicum.catsgram.dao.PostDao;
import ru.yandex.practicum.catsgram.exception.PostNotFoundException;
import ru.yandex.practicum.catsgram.exception.UserNotFoundException;
import ru.yandex.practicum.catsgram.model.Post;
import ru.yandex.practicum.catsgram.model.PostFeedParams;
import ru.yandex.practicum.catsgram.model.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService {
    private static final Logger log = LoggerFactory.getLogger(PostService.class);
    private final PostDao postDao;
    private final FollowDao followDao;
    private final UserService userService;
    private final List<Post> posts = new ArrayList<>();

    @Autowired
    public PostService(PostDao postDao, FollowDao followDao, UserService userService) {
        this.postDao = postDao;
        this.followDao = followDao;
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

    public Post getPostById(int postId) {
        return posts.stream()
                .filter(post -> (post.getId() == postId))
                .findFirst()
                .orElseThrow(() -> new PostNotFoundException(String.format("Пост № %d не найден", postId)));
    }

    public Post create(Post post) {
        if (post.getAuthor() == null) {
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

    public Collection<Post> findPostsByUser(String userId) {
        User user = userService.findUserById(userId).orElseThrow(() ->
                new UserNotFoundException("Пользователь с идентификатором " + userId + " не найден."));
        return postDao.findAllByUser(user);
    }

    public Collection<Post> findPostsByUser(String userId, Integer size, String sort) {
        Comparator<Post> comparator = Comparator.comparing(Post::getCreationDate);
        if (sort.equals("desc")) {
            comparator = comparator.reversed();
        }
        return findPostsByUser(userId)
                .stream()
                .sorted(comparator)
                .limit(size)
                .collect(Collectors.toList());
    }

    public List<Post> getFollowFeed(String userId, int size) {
        return followDao.getFollowFeed(userId, size);
    }

    public List<Post> getFollowFeed2(String userId, int size) {
        return followDao.getFollowFeed2(userId, size);
    }
}
