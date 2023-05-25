package com.boots.repository;

import com.boots.entity.Message;
import com.boots.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message,Long> {
    List<Message> findAllBySenderAndReceiver(User Sender, User Receiver);
    List<Message> findAll();
}
