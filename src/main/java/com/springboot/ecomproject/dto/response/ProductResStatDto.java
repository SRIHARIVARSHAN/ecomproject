package com.springboot.ecomproject.dto.response;

public record ProductResStatDto(
        String sellerName,
        long numberOfProductsOwned
) {
}