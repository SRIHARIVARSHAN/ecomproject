package com.springboot.ecomproject.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String reviewText;
    private int rating;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    // Review cannot be posted without the product
    private Product product;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    // Review cannot be posted without the customer
    private Customer customer;
}
/*
Since Review has a SINGLE customer and SINGLE product attached to it, Hibernate
will fetch it along with the Review.
However, do note that,
if there are further joins in Customer and Product entities, then we need to ensure that
indirectly connected entities are not fetched.
* */