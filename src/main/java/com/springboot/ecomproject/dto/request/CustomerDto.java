package com.springboot.ecomproject.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record CustomerDto(
        @NotBlank(message = "Name cannot be empty")
        @Pattern(regexp = "[a-zA-Z ]+", message = "Name cannot contain special characters and numbers.")
        @Size(min=2,message = "Name must be at least 2 characters")
        String name,

        @NotBlank(message = "city cannot be empty")
        String city,

        String username,

        String password
) {
}
