package com.example.publicationdocuments.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import com.example.publicationdocuments.service.UserService;



@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Autowired
    private UserDetailsService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Autowired
    public WebSecurityConfig(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/",
                                "/home",
                                "/webjars/**",
                                "/images/**"
                        ).permitAll()
                        .anyRequest().authenticated()                              
                )
                .formLogin((form) -> form
                        .loginPage("/login")
                        .permitAll()
                        .successHandler((request, response, authentication) -> {
                            var savedRequest = (org.springframework.security.web.savedrequest.DefaultSavedRequest) request
                                    .getSession()
                                    .getAttribute("SPRING_SECURITY_SAVED_REQUEST");
                            if (savedRequest != null) {
                                response.sendRedirect(savedRequest.getRequestURL());
                            } else {
                                response.sendRedirect("/documents");                     
                            }
                        }))
                .exceptionHandling((exceptionHandling) -> exceptionHandling
                        .accessDeniedPage("/access-denied"))
                .logout((logout) -> logout.permitAll());

        return http.build();
    }
  
        
    @Bean
    public AuthenticationProvider authenticationProvider() {
            DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
            authenticationProvider.setUserDetailsService(userService);
            authenticationProvider.setPasswordEncoder(passwordEncoder);
            return authenticationProvider;
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user = User.withUsername("admin")
                .password(
                        "{bcrypt}$2a$10$dXJ3SW6G7P50lGmMkkmwe.20cQQubK3.HZWzG3YB1tlRy.fqvM/BG")
                .roles("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(user);

    }

}