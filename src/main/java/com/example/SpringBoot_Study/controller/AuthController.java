package com.example.SpringBoot_Study.controller;

import com.example.SpringBoot_Study.model.User;
import com.example.SpringBoot_Study.service.CustomUserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private final CustomUserDetailsService service;

    public AuthController(CustomUserDetailsService service) {
        this.service = service;
    }

    //ログインページ
    @GetMapping("/login")
    public String login() {
        return "auth/login";
    }

    //登録ページ
    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("user", new User());
        return "auth/register";
    }

    //登録処理
    @PostMapping("/register")
    public String register(@ModelAttribute User user) {
        service.registerUser(user);
        return "regirect:/auth/login";
    }
}
