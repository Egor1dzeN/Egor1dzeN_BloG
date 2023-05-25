package com.boots.controller;

import com.boots.entity.User;
import com.boots.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

@Controller
public class RegistrationController {

    @Autowired
    private UserService userService;

    @GetMapping("/registration")
    public String registration(Model model) {
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(@RequestParam String username,@RequestParam String ps1,@RequestParam String ps2, Model model) {
        String error = "";
        if (!ps1.equals(ps2)){
            error = "Пароли не совпадают";
            model.addAttribute("error", error);
            return "registration";
        }
        User user = new User(username,ps1);
        user.setPreviewImageId(1L);
        if (!userService.saveUser(user)){
            error = "Пользователь с таким именем уже существует";
            model.addAttribute("error", error);
            return "registration";
        }

        return "redirect:/";
    }
}
