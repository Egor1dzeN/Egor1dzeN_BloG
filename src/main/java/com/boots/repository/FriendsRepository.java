package com.boots.repository;

import com.boots.entity.Friends;
import com.boots.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FriendsRepository extends JpaRepository<Friends, Long> {
    List<Friends> findAllByUser1AndUser2(User user1, User user2);
    Friends findByUser1AndUser2(User user1, User user2);
    List<Friends> findAllByUser1(User user1);
    List<Friends> findAllByUser2(User user2);
}
