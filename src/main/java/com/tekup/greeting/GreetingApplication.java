package com.tekup.greeting;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.tekup.greeting.bussiness.services.UserService;


@SpringBootApplication
public class GreetingApplication {
	@Autowired
	UserService userService;
	public static void main(String[] args) {
		SpringApplication.run(GreetingApplication.class, args);
	}
}
