package com.project.billingsystem.services;

import com.project.billingsystem.dtos.AuthenticationRequest;
import com.project.billingsystem.dtos.RegisterDto;


public interface Services {

    void register(RegisterDto registerDto);

    String authenticate(AuthenticationRequest request);
}
