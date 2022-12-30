package com.project.billingsystem.services;

import com.project.billingsystem.dtos.RegisterDto;
import com.project.billingsystem.exceptions.*;
import com.project.billingsystem.models.AppUser;
import com.project.billingsystem.repositories.AppUserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ServiceImp implements Services {
    private final AppUserRepository appUserRepository;

    private final PasswordEncoder passwordEncoder;

    public ServiceImp(AppUserRepository appUserRepository, BCryptPasswordEncoder passwordEncoder) {
        this.appUserRepository = appUserRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void register(RegisterDto registerDto) {
        if (registerDto == null) {
            throw new IllegalArgumentException();
        }
        if (registerDto.email().isEmpty() || registerDto.username().isEmpty() || registerDto.password().isEmpty()) {
            if (registerDto.email().isEmpty()) {
                throw new EmailIsMissingException();
            } else if (registerDto.username().isEmpty()) {
                throw new UsernameIsMissingException();
            }
            throw new PasswordIsMissingException();
        } else if (appUserRepository.existsAppUserByUsername(registerDto.username()) || appUserRepository.existsAppUserByEmail(registerDto.email())) {
            if (appUserRepository.existsAppUserByUsername(registerDto.username())) {
                throw new UsernameIsAlreadyTakenException();
            }
            throw new EmailIsAlreadyUsedException();
        } else if (!validatePassword(registerDto.password())) {
            throw new PasswordNotValidException();
        }
        String encodedPassword = passwordEncoder.encode(registerDto.password());
        AppUser appUser = new AppUser(registerDto.username(), encodedPassword, registerDto.password());
        appUserRepository.save(appUser);
    }

    private boolean validatePassword(String password) {
        String regex = "^(?=.*[0-9])"
                + "(?=.*[a-z])(?=.*[A-Z])"
                + "(?=\\S+$).{8,20}$";
        Pattern pattern = Pattern.compile(regex);
        if (pattern == null) {
            return false;
        }
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }
}
