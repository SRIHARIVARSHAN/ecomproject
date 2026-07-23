package com.springboot.ecomproject.mapper;

import com.springboot.ecomproject.dto.response.OrderDto;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

public class OrderMapper {
    public static OrderDto processOrder(OrderDto dto){
        return new OrderDto(
                dto.productId(),
                dto.productTitle(),
                dto.actualPrice(),
                dto.discount(),
                dto.quantity(),
                dto.dateOfPurchase(),
                dto.sellerName(),
                dto.paidPrice(),
                dto.rating() != 0 || dto.reviewText() != null,
                dto.rating(),
                dto.reviewText(),
                dto.deliveredDate() != null &&
                        Instant.now().minus(4, ChronoUnit.DAYS).isBefore(dto.deliveredDate()),
                dto.deliveredDate()
        );
    }
}
