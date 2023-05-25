package com.boots.controller;


import com.boots.entity.User;
import com.boots.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Date;
import java.util.List;

@Controller
public class MainRepository {
    @Autowired
    UserRepository userRepository;

    @GetMapping(value = "/")
    public String main(Model model){
        String enter_username = "Войти",ref_enter_username = "/login";
        String registration_exit = "Регистрация",ref_registration_exit = "/registration";
        String messager_no = "",ref_messanger_no = "/messanger";

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken)) {
            UserDetails userDetails = (UserDetails) auth.getPrincipal();
            System.out.println(userDetails.getUsername());
            enter_username = userDetails.getUsername();
            ref_enter_username = "/client/"+enter_username;
            registration_exit = "Выйти";
            ref_registration_exit= "/logout";
            messager_no = "Сообщения";
            model.addAttribute("enter_username",enter_username);
            model.addAttribute("ref_enter_username",ref_enter_username);
            model.addAttribute("ref_registration_exit",ref_registration_exit);
            model.addAttribute("registration_exit",registration_exit);
            model.addAttribute("messanger_no",messager_no);
            model.addAttribute("ref_messanger_no",ref_messanger_no);
            model.addAttribute("user",userRepository.findByUsername(userDetails.getUsername()));
            return "index";
        }
        else {
            return "index_no_login";
        }

    }
}
