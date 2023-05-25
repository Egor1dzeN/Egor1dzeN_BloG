package com.boots.controller;


import com.boots.entity.User;
import com.boots.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class On_OfflineController {
    @Autowired
    UserRepository userRepository;

    @RequestMapping(value = "/update_time", method = RequestMethod.POST)
    @ResponseBody
    public String update_time(@RequestBody String username){
        System.out.println(username);
        User user = userRepository.findByUsername(username);
        Date date = new Date();
        System.out.println(date);
        user.setActiveDateTime(date);
        System.out.println(user.getActiveDateTime());
        userRepository.save(user);
        return "working";
    }
    @RequestMapping(value = "/get_time/{username}",method = RequestMethod.GET)
    @ResponseBody
    public String get_time(@PathVariable(name = "username")String username){
        User user = userRepository.findByUsername(username);
        String DATE_FORMAT_NOW = "yyyy:MM:dd:HH:mm:ss";
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
        String ans = sdf.format(user.getActiveDateTime());
        System.out.println(ans);
        return ans;
    }
}
