package com.malak.todolist.controllers;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;



@RestController
public class Home {
    @GetMapping("/")
    public String homeController() {
        return "Welcome to the To-Do List Application!";
    }
    
}
