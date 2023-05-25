package com.boots.entity;


import net.bytebuddy.implementation.bind.annotation.Default;

import javax.persistence.*;

@Entity
public class Friends {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user1_id")
    private User user1;
    @ManyToOne
    @JoinColumn(name = "user2_id")
    private User user2;

    private int type_friends;

    public Friends(User user1, User user2, int type_friends) {
        this.user1 = user1;
        this.user2 = user2;
        this.type_friends = type_friends;
    }

    public Friends(Long id, User user1, User user2, int type_friends) {
        this.id = id;
        this.user1 = user1;
        this.user2 = user2;
        this.type_friends = type_friends;
    }

    public Friends() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser1() {
        return user1;
    }

    public void setUser1(User user1) {
        this.user1 = user1;
    }

    public User getUser2() {
        return user2;
    }

    public void setUser2(User user2) {
        this.user2 = user2;
    }

    public int getType_friends() {
        return type_friends;
    }

    public void setType_friends(int type_friends) {
        this.type_friends = type_friends;
    }
}
