package com.springboot.ecomproject.controller;

import com.springboot.ecomproject.dto.request.ProductReqDto;
import com.springboot.ecomproject.dto.response.OrderDto;
import com.springboot.ecomproject.dto.response.ProductResStatDto;
import com.springboot.ecomproject.dto.response.ProductRespDto;
import com.springboot.ecomproject.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/product")
public class ProductController {

    private final ProductService productService;

    @PostMapping("/add/{sellerId}/{categoryId}")
    public void add(@PathVariable long sellerId,
                        @PathVariable long categoryId,
                        @Valid @RequestBody ProductReqDto productReqDto){
         productService.add(sellerId,categoryId,productReqDto);
    }

    @GetMapping("/by-category/{categoryId}")
    public List<ProductRespDto> getByCategoryId(@PathVariable long categoryId,
                                                @RequestParam(required = false,defaultValue = "1") int page,
                                                @RequestParam(required = false,defaultValue = "50") int size
                                                ){
        return productService.getByCategoryId(categoryId,page,size);
    }

    @GetMapping("/count/for-each-seller")
    public List<ProductResStatDto> getProductForEachSeller(){
        return productService.getProductForEachSeller();
    }

    @GetMapping("/purchase/by-customer")
    public List<OrderDto> getProductsPurchasedByCustomerUsername(Principal principal,
                                                                 @RequestParam(required = false, defaultValue = "0") int page,
                                                                 @RequestParam(required = false, defaultValue = "5") int size) {
        String customerUsername=principal.getName();
        return productService.getProductsPurchasedByCustomerUsername(customerUsername, page, size);
    }
}
