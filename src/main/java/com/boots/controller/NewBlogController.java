package com.boots.controller;


import com.boots.entity.Blogs;
import com.boots.entity.User;
import com.boots.repository.BlogsRepository;
import com.boots.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class NewBlogController {
    @Autowired
    BlogsRepository blogsRepository;
    @Autowired
    UserService userService;

    @GetMapping(value = "/blog/new")
    public String new_blog(Model model){
        model.addAttribute("user",userService.get_user());
        return "blog_new";
    }
    @PostMapping(value = "/blog/new")
    public String new_blog_post(@RequestParam String text_blog){
        User user = userService.get_user();
        Blogs blogs = new Blogs(text_blog,user);

        blogsRepository.save(blogs);
        return "redirect:/news";
    }
}
