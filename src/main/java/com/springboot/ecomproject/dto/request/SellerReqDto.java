package com.springboot.ecomproject.dto.request;

import jakarta.validation.constraints.NotBlank;

public record SellerReqDto(
        @NotBlank(message = "Field is mandatory")
        String name,
        @NotBlank(message = "Field is mandatory")
        String contact,
        @NotBlank(message = "Field is mandatory")
        String city,
        @NotBlank(message = "Field is mandatory")
        String username,
        @NotBlank(message = "Field is mandatory")
        String password
)
{
}
