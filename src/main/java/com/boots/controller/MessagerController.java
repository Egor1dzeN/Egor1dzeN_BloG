package com.boots.controller;


import com.boots.entity.Blogs;
import com.boots.entity.Friends;
import com.boots.entity.Message;
import com.boots.entity.User;
import com.boots.repository.FriendsRepository;
import com.boots.repository.MessageRepository;
import com.boots.repository.UserRepository;
import com.boots.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.xml.crypto.Data;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Controller
public class MessagerController {

    @Autowired
    UserRepository userRepository;
    @Autowired
    MessageRepository messageRepository;

    /*@GetMapping(value = "/messanger")
    public String messaner(Model model){
        List<User> list= userRepository.findAll();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        User client = userRepository.findByUsername(userDetails.getUsername());
        list.remove(client);
        model.addAttribute("list",list);
        model.addAttribute("user", client);
        return "message";
    }
    @GetMapping(value = "/messen/{id}")
    public String messanger_id(@PathVariable(value = "id")Long id, Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        User reciver = userRepository.findUserById(id);
        List<Message> list = messageRepository.findAll();
        List<Message> all = new ArrayList<>();
        for(Message chel:list){
            if((chel.getSender().equals(reciver.getUsername()) && chel.getReceiver().equals(userDetails.getUsername()))
                ||(chel.getSender().equals(userDetails.getUsername()) && chel.getReceiver().equals(reciver.getUsername()))){
                all.add(chel);
            }
        }
        System.out.println(all.size());
        model.addAttribute("sandler",userDetails.getUsername());
        model.addAttribute("reciver",reciver.getUsername());
        model.addAttribute("all_message",all);
        model.addAttribute("id",id);
        model.addAttribute("user", userRepository.findByUsername(userDetails.getUsername()));
        return "message_to";
    }
    @PostMapping(value = "/message/sand")
    public String sand_massage(@RequestParam String text_message,@RequestParam String reciver,@RequestParam String sandler,@RequestParam String id){
        Date date = new Date(System.currentTimeMillis());
        String ans = "redirect:/messenger/"+id;
        return ans;
    }*/
    @Autowired
    UserService userService;
    @Autowired
    FriendsRepository friendsRepository;
    @GetMapping("/messanger/{id}")
    public String get_messanger_id(@PathVariable Long id, Model model){
        User user = userService.get_user(); //I
        User friend = userRepository.findUserById(id); //Friend
        List<User> userList = new ArrayList<>();
        List<Friends> friendsList = friendsRepository.findAll();
        for(Friends friends:friendsList){
            if(friends.getUser1() == user  && friends.getType_friends() == 3){
                userList.add(friends.getUser2());
            }
            else if(friends.getUser2() == user  && friends.getType_friends() == 3){
                userList.add(friends.getUser1());
            }
        }
        model.addAttribute("user",user);
        model.addAttribute("userList",userList);
        System.out.println(userList.size());
        if(id==0){
            model.addAttribute("id", "0");
        }
        else{
            model.addAttribute("id",id);
            List<Message> messageList = messageRepository.findAll();
            List<Message> sort_messageList = new ArrayList<>();
            for(Message message: messageList){
                if((message.getSender() == user && message.getReceiver() == friend) || (message.getReceiver() == user && message.getSender() == friend)){
                    sort_messageList.add(message);
                }
            }
            model.addAttribute("listMessage", sort_messageList);
            model.addAttribute("user", user);
            model.addAttribute("friend", friend);
        }
        return "message";
    }
    @PostMapping("/send/message")
    public String send_messgae(@RequestParam String text_message, @RequestParam String id){
        User i = userService.get_user();
        User friend = userRepository.findUserById(Long.valueOf(id));
        Message message = new Message(i,friend,text_message,new Date());
        messageRepository.save(message);
        return "redirect:/messanger/"+id;
    }
}

