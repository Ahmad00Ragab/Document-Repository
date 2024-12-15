package com.tekup.greeting.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.tekup.greeting.web.models.User;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMethod;


@Slf4j
@Controller
public class GreetingController {

   @RequestMapping({"/","/home"})
   public String showHome() {
       return "home";
   }
   

    @RequestMapping("/greeting")
    private String greeting(@RequestParam(value="name",required = false, defaultValue = "World") String name,
                            @RequestParam int age,
                            Model model){
        //model.addAttribute("name","World");
        String [] names=new String[]{"demo1","demo2","demo3"};
         model.addAttribute("names", names);
         model.addAttribute("user", new User("demo","manag"));
         model.addAttribute("name",name);
         model.addAttribute("age", age);
         log.info("User: "+name);
        return "greeting";
    }
 


}