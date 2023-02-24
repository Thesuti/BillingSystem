package com.project.billingsystem.controllers;

import com.project.billingsystem.dtos.AuthenticationRequest;
import com.project.billingsystem.dtos.RegisterDto;
import com.project.billingsystem.models.AppUser;
import com.project.billingsystem.repositories.AppUserRepository;
import com.project.billingsystem.services.EmailService;
import com.project.billingsystem.services.Services;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class MainController {

    public MainController(Services services, EmailService emailService,
                          AppUserRepository appUserRepository) {
        this.services = services;
        this.emailService = emailService;
        this.appUserRepository = appUserRepository;
    }

    private final Services services;

    private final EmailService emailService;
    private final AppUserRepository appUserRepository;

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
    public ResponseEntity register(@RequestBody RegisterDto registerDto){
        services.register(registerDto);
        return ResponseEntity.ok().body("Successfully registered");
    }

    @PostMapping("/authenticate")
    public ResponseEntity register(@RequestBody AuthenticationRequest request){
        return ResponseEntity.ok().body(services.authenticate(request));
    }

    //Dont delete it. It help in testing purposes.
    /*@GetMapping("/sendemail")
    public ResponseEntity sendEmail(){
        List<AppUser> appUserList = appUserRepository.findAll();
        for (AppUser appUser: appUserList) {
            emailService.sendEmail(appUser.getEmail());
        }
       return ResponseEntity.ok().body("Email sent to all AppUsers");
    }*/

}
