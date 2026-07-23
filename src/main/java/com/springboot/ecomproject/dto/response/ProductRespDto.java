package com.springboot.ecomproject.dto.response;

public record ProductRespDto(
        String title,
        Long id,
        double price,
        String sellerName

) {
}
