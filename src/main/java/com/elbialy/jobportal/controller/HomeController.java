package com.elbialy.jobportal.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController

public class HomeController {
    @GetMapping
    public Map<String,String >homePage(){
        Map<String ,String> response = new HashMap<>();
        String message = "hello in our Platform";
        response.put("login","/login");
        response.put("register","/register");
        response.put("message",message);
        return response;
    }
}
