package com.boots.repository;

import com.boots.entity.Blogs;
import com.boots.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BlogsRepository extends JpaRepository<Blogs,Long> {
    List<Blogs> findAllByUser(User user);
    Blogs findAllById(Long id);
}
