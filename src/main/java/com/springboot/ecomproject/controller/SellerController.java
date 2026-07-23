package com.springboot.ecomproject.controller;

import com.springboot.ecomproject.dto.request.SellerReqDto;
import com.springboot.ecomproject.service.SellerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/seller")
@RequiredArgsConstructor
public class SellerController {
        private final SellerService sellerService;

        /*
        Body: {
            name: "",
            contact: "",
            city: "",
            username: "",
            password: ""
        }
         */

        @PostMapping("/add")
        public void insert(Principal principal,
                           @Valid @RequestBody SellerReqDto sellerReqDto) {
            String executiveUsername=principal.getName();
            sellerService.insert(executiveUsername, sellerReqDto);
        }

        @DeleteMapping("/de-activate")
        public void deActivateSeller(Principal principal){
            String sellerUsername= principal.getName();
            sellerService.deActivateSeller(sellerUsername);
        }
    }

