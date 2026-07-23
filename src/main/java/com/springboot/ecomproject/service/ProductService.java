package com.springboot.ecomproject.service;

import com.springboot.ecomproject.dto.request.ProductReqDto;
import com.springboot.ecomproject.dto.response.OrderDto;
import com.springboot.ecomproject.dto.response.ProductResStatDto;
import com.springboot.ecomproject.dto.response.ProductRespDto;
import com.springboot.ecomproject.exception.ResourceNotFoundException;
import com.springboot.ecomproject.mapper.OrderMapper;
import com.springboot.ecomproject.mapper.ProductMapper;
import com.springboot.ecomproject.model.Category;
import com.springboot.ecomproject.model.Product;
import com.springboot.ecomproject.model.Seller;
import com.springboot.ecomproject.repository.CategoryRepository;
import com.springboot.ecomproject.repository.ProductRepository;
import com.springboot.ecomproject.repository.SellerRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final SellerRepository sellerRepository;
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public void add(long sellerId, long categoryId, @Valid ProductReqDto productReqDto) {
        Seller seller = sellerRepository.findById(sellerId)
                .orElseThrow();

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(()-> new ResourceNotFoundException("Category id invalid."));

        Product product = ProductMapper.convertDtoToEntity(productReqDto);

        product.setSeller(seller);
        product.setCategory(category);

        productRepository.save(product);
    }

    public List<ProductRespDto> getByCategoryId(long categoryId, int page, int size) {
        Pageable pageable= PageRequest.of(page,size);
        List<Product> list=productRepository.getCategoryByIdV1(categoryId,pageable);
        return list
                .stream()
                .map(ProductMapper::convertEntityToDto)
                .toList();

    }

    public List<ProductResStatDto> getProductForEachSeller() {
        return productRepository.getProductForEachSeller();
    }

    public List<OrderDto> getProductsPurchasedByCustomerUsername(String customerUsername, int page, int size) {
        Pageable pageable=PageRequest.of(page,size);

        List<OrderDto> list=productRepository.getProductsPurchasedByCustomerUsername(customerUsername,pageable);
        return list.stream()
                .map(OrderMapper::processOrder)
                .toList();


    }
}
