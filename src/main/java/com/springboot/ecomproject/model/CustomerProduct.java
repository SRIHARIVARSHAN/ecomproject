package com.springboot.ecomproject.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "customer_product")
public class CustomerProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @CreationTimestamp
    private Instant purchaseDate;

    private Instant deliveredDate;

    private int qty;
    private double discount;

    @ManyToOne
    @JoinColumn(name = "customer_id" , nullable = false)
    private Customer customer;


}