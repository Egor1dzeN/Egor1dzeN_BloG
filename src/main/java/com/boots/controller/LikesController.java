package com.boots.controller;


import com.boots.entity.Blogs;
import com.boots.entity.User;
import com.boots.repository.BlogsRepository;
import com.boots.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Controller
public class LikesController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    BlogsRepository blogsRepository;
    @PostMapping("/like/{id}")
    public ResponseEntity<?> post_like_info(@PathVariable Long id, @RequestBody String username){
        User user = userRepository.findByUsername(username);
        Blogs blogs = blogsRepository.findAllById(id);
        List<User> list = blogs.getLikes();
//        list.add(user);
        if(list.contains(user)){
            System.out.println("exist");
            list.remove(user);
        }else{
            System.out.println("no exist");
            list.add(user);
        }
        blogs.setLikes(list);
        blogsRepository.save(blogs);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
