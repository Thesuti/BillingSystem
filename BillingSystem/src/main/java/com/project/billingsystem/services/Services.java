package com.project.billingsystem.services;

import com.project.billingsystem.dtos.AuthenticationRequest;
import com.project.billingsystem.dtos.AuthenticationResponse;
import com.project.billingsystem.dtos.RegisterDto;


public interface Services {

    String register(RegisterDto registerDto);

    void sendNotification();

    String authenticate(AuthenticationRequest request);
}
