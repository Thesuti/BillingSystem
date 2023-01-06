package com.project.billingsystem.services;

import com.project.billingsystem.configurations.JwtService;
import com.project.billingsystem.dtos.RegisterDto;
import com.project.billingsystem.exceptions.*;
import com.project.billingsystem.models.AppUser;
import com.project.billingsystem.dtos.AuthenticationRequest;
import com.project.billingsystem.dtos.AuthenticationResponse;
import com.project.billingsystem.models.Role;
import com.project.billingsystem.repositories.AppUserRepository;
import jakarta.mail.Message;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.Builder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Builder
public class ServiceImp implements Services {

    @Value("{EMAIL_$USERNAME}")
    private String username;

    @Value("{$EMAIL_PASSWORD}")
    private String password;
    @Value("{$EMAIL_FROM}")
    private String email;

    @Value("{$EMAIL_HOST}")
    private String host;

    private final AppUserRepository appUserRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    public ServiceImp(AppUserRepository appUserRepository, PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.appUserRepository = appUserRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public AuthenticationResponse register(RegisterDto registerDto) {
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
        appUser.setRole(Role.USER);
        appUserRepository.save(appUser);
        var jwtToken = jwtService.generateToken(appUser);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }


    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(),request.getPassword()));
        var appUser = appUserRepository.findAppUserByUsername(request.getUsername()).orElseThrow(() -> new UsernameNotFoundException(""));
        var jwtToken = jwtService.generateToken(appUser);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public void sendNotification() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 13);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                sendMessage();
            }
        }, calendar.getTime());
    }

    private void sendMessage() {
        List<AppUser> appUserList = appUserRepository.findAll();
        for (AppUser appUser : appUserList) {
            if (appUser.getBalance() >= 1000) {
                String to = appUser.getEmail();
                Properties properties = new Properties();
                properties.put("mail.smtp.auth", "true");
                properties.put("mail.smtp.starttls.enable", "true");
                properties.put("mail.smtp.host", host);
                properties.put("mail.smtp.port", "587");

                Session session = Session.getInstance(properties,
                        new jakarta.mail.Authenticator() {
                            protected PasswordAuthentication getPasswordAuthentication() {
                                return new PasswordAuthentication(username, password);
                            }
                        });
                try {
                    Message message = new MimeMessage(session);
                    message.setFrom(new InternetAddress(email));
                    message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
                    message.setSubject("Warning");
                    message.setText("You have " + appUser.getBalance() + "leeway on your account");
                    Transport.send(message);
                } catch (Exception exception) {
                    System.out.println(exception.getMessage());
                }

            }
        }
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
