package com.example.publicationdocuments.controller;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;

@Controller
public class HomeController {
    @RequestMapping({"/","/home"})
    public String showHome() {
        return "home";
    }
}
