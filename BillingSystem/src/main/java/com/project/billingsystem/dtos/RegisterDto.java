package com.project.billingsystem.dtos;

import lombok.Builder;

@Builder
public record RegisterDto(String username,String email,String password) {
}
