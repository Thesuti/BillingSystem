package com.project.billingsystem.controllers;

import com.project.billingsystem.repositories.AppUserRepository;
import com.project.billingsystem.services.ProfileService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProfileController {

    private final AppUserRepository appUserRepository;

    private final ProfileService profileService;

    public ProfileController(AppUserRepository appUserRepository, ProfileService profileService) {
        this.appUserRepository = appUserRepository;
        this.profileService = profileService;
    }

    @GetMapping("/profile/{profileName}")
    public ResponseEntity getUserProfilePage(@PathVariable String profileName, HttpServletRequest request, Authentication authentication) {
        String token = profileService.extractTokenFromRequest(request);
        System.out.println("Token : " + token);
        //if (profileName.equals(authentication.getName())) {
            return ResponseEntity.ok().body(appUserRepository.findAppUserByUsername(profileName));
        //}
        //return ResponseEntity.status(401).body("Unauthorized");
    }


}
