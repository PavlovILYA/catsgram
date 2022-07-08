package ru.yandex.practicum.catsgram.model;

public class Follow {
    private Integer id;
    private User author;
    private User follower;

    public Follow(Integer id, User author, User follower) {
        this.id = id;
        this.author = author;
        this.follower = follower;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public User getFollower() {
        return follower;
    }

    public void setFollower(User follower) {
        this.follower = follower;
    }
}
