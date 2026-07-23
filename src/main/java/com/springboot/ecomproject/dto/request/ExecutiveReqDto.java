package com.springboot.ecomproject.dto.request;

import com.springboot.ecomproject.enums.JobTitle;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ExecutiveReqDto(
        @NotBlank(message = "Name is mandatory")
        String name,
        JobTitle jobTitle,
        @NotBlank(message = "Username is mandatory")
        String username,
        @NotBlank(message = "password is mandatory")
        @Size(min = 5, max=15 , message = "Password should be min 5 and max 15 chars")
        String password) {
}
