package com.springboot.ecomproject.service;

import com.springboot.ecomproject.dto.request.SellerReqDto;
import com.springboot.ecomproject.enums.Role;
import com.springboot.ecomproject.exception.ResourceNotFoundException;
import com.springboot.ecomproject.mapper.SellerMapper;
import com.springboot.ecomproject.mapper.UserMapper;
import com.springboot.ecomproject.model.Executive;
import com.springboot.ecomproject.model.Product;
import com.springboot.ecomproject.model.Seller;
import com.springboot.ecomproject.model.User;
import com.springboot.ecomproject.repository.ExecutiveRepository;
import com.springboot.ecomproject.repository.ProductRepository;
import com.springboot.ecomproject.repository.SellerRepository;
import com.springboot.ecomproject.repository.UserRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SellerService {
    private final SellerRepository sellerRepository;
    private final ExecutiveRepository executiveRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final PasswordEncoder passwordEncoder;

    public void insert(String executiveUsername, @Valid SellerReqDto sellerReqDto) {
        // Step 1: Fetch Executive using given executiveId
        Executive executive = executiveRepository.findByUserUsername(executiveUsername);

        // Step 2: Fetch User details from dto and save it in DB
        User user =  UserMapper.convertDtoToEntity(     // this user is without id
                sellerReqDto.username(),
                sellerReqDto.password(),
                Role.SELLER);

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setActivated(false);
        user = userRepository.save(user); // this user reference has an id attached.

        // Step 3: Fetch seller from dto
        Seller seller = SellerMapper.convertDtoToEntity(sellerReqDto);

        // Step 4: Attach user and executive to this seller
        seller.setUser(user);
        seller.setExecutive(executive);

        // Step 5: Save Seller in DB
        sellerRepository.save(seller);
    }

    @Transactional
    public void deActivateSeller(String sellerUsername) {
        //fetch seller
        Seller seller=sellerRepository.getSellerByUsername(sellerUsername).orElseThrow(()-> new ResourceNotFoundException("Seller not found"));

        //update isActive to false and save in db;

        seller.getUser().setActivated(false);
        sellerRepository.save(seller);

        //fetch the products of the seller

        List<Product> list=productRepository.getProductBySellerId(seller.getId());

        //making the product quantity to 0
        list=list.stream()
                .peek( p->p.setStockCount(0))
                .toList();

        productRepository.saveAll(list);
    }
}
