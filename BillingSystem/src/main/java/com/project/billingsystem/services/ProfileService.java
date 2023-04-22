package com.project.billingsystem.services;

import com.project.billingsystem.models.AppUser;
import com.project.billingsystem.repositories.AppUserRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class ProfileService {
    private final AppUserRepository appUserRepository;

    public ProfileService(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

    public String extractTokenFromRequest(HttpServletRequest request){
        String token = request.getHeader("Authorization");
        if(StringUtils.hasText(token) && token.startsWith("Bearer ")){
            return token.substring(7);
        }
        return null;
    }

    public String asd(Authentication authentication,HttpServletRequest request){
        System.out.println("Authorities : "+authentication.getAuthorities());
        System.out.println("Authentication name : "+authentication.getName());
        return request.getHeader("Username");
    }
}
