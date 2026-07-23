package com.springboot.ecomproject.dto.response;

public record TokenDto(
        String token,
        String expiration,
        String role
) {
}