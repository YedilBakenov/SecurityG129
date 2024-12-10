package com.example.SecurityG129.controller;

import com.example.SecurityG129.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final UserService userService;

    @GetMapping(value = "/")
    public  String getHomePage(){
        return "index";
    }


}
