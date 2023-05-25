package com.boots.entity;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "blogs")
public class Blogs {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String text_blog;
    @ManyToMany
    private List<User> likes;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Blogs(String text_blog,  User user) {
        this.text_blog = text_blog;
        likes = new ArrayList<>();
        this.user = user;
    }

    public Blogs() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText_blog() {
        return text_blog;
    }

    public void setText_blog(String text_blog) {
        this.text_blog = text_blog;
    }

    public List<User> getLikes() {
        return likes;
    }

    public void setLikes(List<User> likes) {
        this.likes = likes;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
