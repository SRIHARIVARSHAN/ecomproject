package com.springboot.ecomproject.mapper;

import com.springboot.ecomproject.dto.request.SellerReqDto;
import com.springboot.ecomproject.model.Seller;
import org.springframework.stereotype.Component;

@Component
public class SellerMapper {
    public static Seller convertDtoToEntity(SellerReqDto sellerReqDto) {
        Seller seller = new Seller();
        seller.setName(sellerReqDto.name());
        seller.setContact(sellerReqDto.contact());
        seller.setCity(sellerReqDto.city());
        return seller;
    }
}
