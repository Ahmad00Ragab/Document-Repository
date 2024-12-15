package com.tekup.greeting.web.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {
    
    @GetMapping("/login")
    public String showFormLogin(Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            // Redirect to a dashboard or home page
            return "redirect:/home";
        }
        return "login";
    }
    
    
    @GetMapping("/access-denied")
    public String getAccessDeniedPage(Model model) {
        model.addAttribute("error", "You are not allowed to access this page");
        return "errors";
    }
}