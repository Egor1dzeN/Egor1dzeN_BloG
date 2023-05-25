package com.boots.controller;


import com.boots.entity.Blogs;
import com.boots.entity.User;
import com.boots.repository.BlogsRepository;
import com.boots.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class NewsController {
    @Autowired
    BlogsRepository blogsRepository;
    @Autowired
    UserService userService;

    @GetMapping(value = "/news")
    public String news(Model model){
        List<User> list_friend = userService.get_all_friends();
        List<Blogs> list_blog_friends = new ArrayList<>();
        for(User user:list_friend){
            list_blog_friends.addAll(blogsRepository.findAllByUser(user));;
        }
        model.addAttribute("list_blogs_friends",list_blog_friends);
        model.addAttribute("user",userService.get_user());
        return "news";
    }
}
