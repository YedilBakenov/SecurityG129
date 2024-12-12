package com.example.SecurityG129.controller;

import com.example.SecurityG129.model.User;
import com.example.SecurityG129.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final UserService userService;

    @PreAuthorize("isAuthenticated()")
    @GetMapping(value = "/")
    public  String getHomePage(){
        return "index";
    }
    @PreAuthorize("isAnonymous()")
    @GetMapping(value = "/sign-in")
    public String getLogin(){
        return "sign-in";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping(value = "/admin")
    public String adminPage(){
        return "admin";
    }

    @GetMapping(value = "/forbidden")
    public String denied(){
        return "forbidden";
    }

    @PreAuthorize("isAnonymous()")
    @GetMapping(value = "/registration")
    public String getRegistrPage(){
        return "registration";
    }

    @PostMapping(value = "/registration")
    public String getRegistr(User user, @RequestParam String rePassword){
        System.out.println(user);
        System.out.println(rePassword);

        userService.addUser(user, rePassword);
        return "redirect:/sign-in";
    }


}
