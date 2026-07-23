package com.springboot.ecomproject.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ProductReqDto(
        @NotBlank(message = "Field is mandatory")
        String title,
        @NotBlank(message = "Field is mandatory")
        String description,
        @NotNull(message = "Field is mandatory")
        @Min(value = 100)
        Double price
) {
}
