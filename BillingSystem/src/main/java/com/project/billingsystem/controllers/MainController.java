package com.project.billingsystem.controllers;

import com.project.billingsystem.dtos.RegisterDto;
import com.project.billingsystem.dtos.AuthenticationRequest;
import com.project.billingsystem.dtos.AuthenticationResponse;
import com.project.billingsystem.services.Services;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

    public MainController(Services services) {
        this.services = services;
    }

    private final Services services;

    /*@GetMapping("/")
    public String welcomePage(){
        return "index";
    }*/
    @GetMapping("/")
    public ResponseEntity welcomePage(){
        return ResponseEntity.ok().body("Hello here Traveler");
    }

    /*@GetMapping("/register")
    public String registerPage(){
        return "register";
    }*/

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterDto registerDto){
        services.register(registerDto);
        return ResponseEntity.ok().body(services.register(registerDto));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody AuthenticationRequest request){
        return ResponseEntity.ok().body(services.authenticate(request));
    }

}
