package ru.yandex.practicum.catsgram.model;

import java.util.List;

public class PostFeedParams {
    private String sort;
    private Integer size;
    private List<String> friends;

    public String getSort() {
        return sort;
    }

    public PostFeedParams setSort(String sort) {
        this.sort = sort;
        return this;
    }

    public Integer getSize() {
        return size;
    }

    public PostFeedParams setSize(Integer size) {
        this.size = size;
        return this;
    }

    public List<String> getFriends() {
        return friends;
    }

    public PostFeedParams setFriends(List<String> friends) {
        this.friends = friends;
        return this;
    }

    @Override
    public String toString() {
        return "PostFeedParams{" +
                "sort='" + sort + '\'' +
                ", size=" + size +
                ", friends=" + friends +
                '}';
    }
}
