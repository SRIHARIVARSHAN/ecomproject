package com.springboot.ecomproject.repository;

import com.springboot.ecomproject.model.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer,Long> {
    @Query("""
            select c
            from Customer c
            where c.id = ?1 AND c.isActive = true
            """)
    Optional<Customer> fetchById(long id);
    @Query("""
            select c
            from Customer c
            where c.isActive = true
            """)
    Page<Customer> fetchAll(Pageable pageable);
    }