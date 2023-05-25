package com.boots.controller;


import com.boots.entity.Friends;
import com.boots.entity.User;
import com.boots.repository.FriendsRepository;
import com.boots.repository.UserRepository;
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

import java.util.ArrayList;
import java.util.List;

@Controller
public class FriendsController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    FriendsRepository friendsRepository;
    List<Friends> itog_list1 = new ArrayList<>();
    List<Friends> itog_list2 = new ArrayList<>();
    List<User> list_find;

    @PostMapping("/client/add_friends/{username}")
    public String add_friend(@PathVariable (value = "username")String username){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        User user1 = userRepository.findByUsername(username);
        User user2 = userRepository.findByUsername(userDetails.getUsername());
        if(user1.getId()>user2.getId()){
            User user3 = user1;
            user1 = user2;
            user2 = user3;
        }
        Friends friends = friendsRepository.findByUser1AndUser2(user1,user2);
        if(friends==null){
            Friends friends1 ;
            if(user1.getUsername().equals(userDetails.getUsername()))
                friends1 = new Friends(user1,user2,1);
            else
                friends1=new Friends(user1,user2,2);
            friendsRepository.save(friends1);
        }
        else if(friends.getType_friends()==0){
            long id = friendsRepository.findByUser1AndUser2(user1,user2).getId();
            friendsRepository.deleteById(id);
            Friends friends1 ;
            if(user1.getUsername().equals(userDetails.getUsername()))
                friends1 = new Friends(user1,user2,1);
            else
                friends1=new Friends(user1,user2,2);
            friendsRepository.save(friends1);
        }
        else
        if(friends.getUser1().getUsername().equals(userDetails.getUsername()) && friends.getType_friends()==2){
            Friends friends1 = new Friends(user1,user2,3);
            long id = friendsRepository.findByUser1AndUser2(user1,user2).getId();
            friendsRepository.deleteById(id);
            friendsRepository.save(friends1);
        }
        else if(friends.getUser2().getUsername().equals(userDetails.getUsername()) && friends.getType_friends()==1){
            Friends friends1 = new Friends(user1,user2,3);
            long id = friendsRepository.findByUser1AndUser2(user1,user2).getId();
            friendsRepository.deleteById(id);
            friendsRepository.save(friends1);
        }

        return "redirect:/client/{username}";
    }

    @PostMapping("/client/delete_friends/{username}")
    public String delete_friend(@PathVariable(value = "username")String username){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        User user1 = userRepository.findByUsername(username);
        User user2 = userRepository.findByUsername(userDetails.getUsername());
        if(user1.getId()>user2.getId()){
            User user3 = user1;
            user1 = user2;
            user2 = user3;
        }
        Friends friends = friendsRepository.findByUser1AndUser2(user1,user2);
        if(friends.getUser1().getUsername().equals(userDetails.getUsername()) && friends.getType_friends()==1){
            Friends friends1 = new Friends(user1,user2,0);
            long id = friendsRepository.findByUser1AndUser2(user1,user2).getId();
            friendsRepository.deleteById(id);
            friendsRepository.save(friends1);
        }
        else if(friends.getUser2().getUsername().equals(userDetails.getUsername()) && friends.getType_friends()==2){
            Friends friends1 = new Friends(user1,user2,0);
            long id = friendsRepository.findByUser1AndUser2(user1,user2).getId();
            friendsRepository.deleteById(id);
            friendsRepository.save(friends1);
        }
        else if(friends.getUser1().getUsername().equals(userDetails.getUsername()) && friends.getType_friends()==3){
            Friends friends1 = new Friends(user1,user2,2);
            long id = friendsRepository.findByUser1AndUser2(user1,user2).getId();
            friendsRepository.deleteById(id);
            friendsRepository.save(friends1);
        }else if(friends.getUser2().getUsername().equals(userDetails.getUsername()) && friends.getType_friends()==3){
            Friends friends1 = new Friends(user1,user2,1);
            long id = friendsRepository.findByUser1AndUser2(user1,user2).getId();
            friendsRepository.deleteById(id);
            friendsRepository.save(friends1);
        }
        return "redirect:/client/{username}";
    }
    @GetMapping("/friends")
    public String get_friends(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        User user = userRepository.findByUsername(userDetails.getUsername());
        List<Friends> list1 = friendsRepository.findAllByUser1(user);
        List<Friends> list2 = friendsRepository.findAllByUser2(user);
        itog_list1 = new ArrayList<>();
        itog_list2 = new ArrayList<>();
        for(Friends friends:list1){
            if (friends.getType_friends()==3)
                itog_list1.add(friends);
        }
        for(Friends friends:list2){
            if (friends.getType_friends()==3)
                itog_list2.add(friends);
        }
        model.addAttribute("list1",itog_list1);
        model.addAttribute("list2",itog_list2);
        model.addAttribute("list",list_find);
        model.addAttribute("user",user);
        return "friends";
    }
    @PostMapping("/friends/find")
    public String friends_find(@RequestParam("username")String username, Model model){
        list_find = userRepository.findByUsernameContainingIgnoreCase(username);

        return "redirect:/friends";
    }
}
