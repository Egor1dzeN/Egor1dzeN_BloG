package com.boots.repository;

import com.boots.entity.Images;
import com.boots.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ImagesRepository extends JpaRepository<Images, Long> {
    List<Images> findByOriginalFileName(String originalFileName);
    List<Images> findByUser(User user);
    Images findAllById(Long id);
    List<Images> findAll();
}
