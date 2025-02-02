package ru.yandex.practicum.catsgram.dao;

import ru.yandex.practicum.catsgram.model.Post;

import java.util.List;

public interface FollowDao {
    List<Post> getFollowFeed(String userId, int size);
    List<Post> getFollowFeed2(String userId, int size);
}
