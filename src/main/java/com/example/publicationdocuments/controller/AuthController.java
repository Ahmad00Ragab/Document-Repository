package com.example.publicationdocuments.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class AuthController {
    
    @GetMapping("/login")
    public String showFormLogin(Authentication authentication) {
        System.out.println("inside login");
        return "login";
    }
    
    
    @GetMapping("/access-denied")
    public String getAccessDeniedPage(Model model) {
        model.addAttribute("error", "You are not allowed to access this page");
        return "errors";
    }   
}