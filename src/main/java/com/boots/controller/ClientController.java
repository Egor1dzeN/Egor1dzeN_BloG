package com.boots.controller;


import com.boots.entity.Blogs;
import com.boots.entity.Friends;
import com.boots.entity.Images;
import com.boots.entity.User;
import com.boots.repository.BlogsRepository;
import com.boots.repository.FriendsRepository;
import com.boots.repository.ImagesRepository;
import com.boots.repository.UserRepository;
import com.boots.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ClientController {

    @Autowired
    UserRepository userRepository;
    @Autowired
    BlogsRepository blogsRepository;
    @Autowired
    FriendsRepository friendsRepository;
    @Autowired
    ImagesRepository imagesRepository;


    @GetMapping(value = "/client/{username}")
    public String client(@PathVariable (value = "username")String username, Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        String i = "",logout = "",ref_logout= "/logout";
        User user = userRepository.findByUsername(username);
        String message = "Сообщение",ref_message  ="/messanger/"+userRepository.findByUsername(username).getId();
        if(userDetails.getUsername().equals(username)){
            return "redirect:/client/me";
        }

        List<Blogs> client_blogs =  blogsRepository.findAllByUser(user);
        User user1 = userRepository.findByUsername(username);
        User user2 = userRepository.findByUsername(userDetails.getUsername());
        if(user1.getId()>user2.getId()){
            User user3 = user1;
            user1 = user2;
            user2 = user3;

        }
        Friends list_friends= friendsRepository.findByUser1AndUser2(user1,user2);
        System.out.println(list_friends);
        if(list_friends == null || list_friends.getType_friends()==0){
            model.addAttribute("add_friend", "Добавить в друзья");
        }
        else{
            Friends friends = friendsRepository.findByUser1AndUser2(user1, user2);
            if(friends.getType_friends() == 1 && user1.getUsername().equals(userDetails.getUsername())){
                model.addAttribute("add_friend", "Вы уже отправили заявку в друзья");
            }
            else if(friends.getType_friends() == 2 && user2.getUsername().equals(userDetails.getUsername())){
                model.addAttribute("add_friend", "Вы уже отправили заявку в друзья");
            }
            else if(friends.getType_friends() == 2 && user1.getUsername().equals(userDetails.getUsername())){
                model.addAttribute("add_friend", "Вам отправили заявку, добавить в друзья");
            }
            else if (friends.getType_friends() == 1 && user2.getUsername().equals(userDetails.getUsername())){
                model.addAttribute("add_friend", "Вам отправили заявку, добавить в друзья");
            }
            else if(friends.getType_friends() == 3){
                model.addAttribute("add_friend", "Вы в друзьях");
            }
            else{
                model.addAttribute("add_friend", "test");
            }

        }

        System.out.println(user1.getId()+" "+user2.getId());
        model.addAttribute("i",i);
        model.addAttribute("logout",logout);
        model.addAttribute("message",message);
        model.addAttribute("ref_logout",ref_logout);
        model.addAttribute("ref_message",ref_message);
        model.addAttribute("username",username);
        model.addAttribute("client_blogs",client_blogs);
        model.addAttribute("user", userService.get_user());
        return "client";
    }

    @GetMapping("/client/me")
    public String client_me(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        User user = userRepository.findByUsername(userDetails.getUsername());
        List<Blogs> client_blogs =  blogsRepository.findAllByUser(userService.get_user());
        model.addAttribute("username", userDetails.getUsername());
        model.addAttribute("client_blogs",client_blogs);
        model.addAttribute("user", userRepository.findByUsername(userDetails.getUsername()));
        if(user.getPreviewImageId()==null){
            user.setPreviewImageId(1L);
        }
        model.addAttribute("id",user.getPreviewImageId());
        List<Images> list = imagesRepository.findAll();
        List<Long> list_id = new ArrayList<>();
        for(Images images:list)
            list_id.add(images.getId());
        model.addAttribute("list_id",list_id);
        model.addAttribute("time",user.getActiveDateTime());
        return "client_me";
    }
    @Autowired
    UserService userService;
    @PostMapping("/add_avatar")
    public String add_avatar(@RequestParam("photo") MultipartFile file) throws IOException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        if(file.getSize()!=0){
            Images images = userService.toImageEntity(file);
            User user = userRepository.findByUsername(userDetails.getUsername());
            images.setUser(user);
            imagesRepository.save(images);
            List<Images> images1 = imagesRepository.findByOriginalFileName(images.getOriginalFileName());
            user.setPreviewImageId(images1.get(0).getId());
            userRepository.save(user);
        }

        return "redirect:/client/me";
    }
}
