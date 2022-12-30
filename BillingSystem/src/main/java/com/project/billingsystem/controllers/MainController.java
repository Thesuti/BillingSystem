package com.project.billingsystem.controllers;

import com.project.billingsystem.dtos.LoginDto;
import com.project.billingsystem.dtos.RegisterDto;
import com.project.billingsystem.services.Services;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MainController {

    public MainController(Services services) {
        this.services = services;
    }

    private final Services services;

    @GetMapping("/")
    public String welcomePage(){
        return "index";
    }

    @GetMapping("/register")
    public String registerPage(){
        return "register";
    }

    @PostMapping("/register")
    public ResponseEntity register(RegisterDto registerDto){
        services.register(registerDto);
        return ResponseEntity.ok().body("Successfully registered");
    }

}
