package com.springboot.ecomproject.repository;

import com.springboot.ecomproject.model.Seller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface SellerRepository extends JpaRepository<Seller,Long> {

    @Query("""
            select s from Seller s where s.user.username=?1
            """)
    Optional<Seller> getSellerByUsername(String sellerUsername);
}
