package com.springboot.ecomproject.mapper;

import com.springboot.ecomproject.dto.request.ProductReqDto;
import com.springboot.ecomproject.dto.response.ProductRespDto;
import com.springboot.ecomproject.model.Product;

public class ProductMapper {
    public static Product convertDtoToEntity(final ProductReqDto productReqDto) {
        Product product=new Product();
        product.setTitle(productReqDto.title());
        product.setDescription(productReqDto.description());
        product.setPrice(productReqDto.price());

        return product;
    }

    public static ProductRespDto convertEntityToDto(Product product) {
        return new ProductRespDto(
                product.getTitle(),
                product.getId(),
                product.getPrice(),
                product.getSeller().getName()
        );

    }
}
