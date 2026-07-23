package com.springboot.ecomproject.repository;

import com.springboot.ecomproject.dto.response.OrderDto;
import com.springboot.ecomproject.dto.response.ProductResStatDto;
import com.springboot.ecomproject.model.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Long> {

    @Query("""
            select p
            from Product p
            where p.category.id = ?1
            """)
    List<Product> getCategoryByIdV1(long categoryId, Pageable pageable);


    @Query("""
            select s.name as sellerName, count(*) as numberOfProductsOwned
            from Product p join p.seller s
            group by s.name
            """)
    List<ProductResStatDto> getProductForEachSeller();

    @Query("""
             select p.id as productId,
             p.title as productTitle,
             p.price as actualPrice,
             cp.discount as discount,
             cp.qty as quantity,
             cp.purchaseDate as dateOfPurchase,
             s.name as sellerName,
             p.price - (p.price * (cp.discount / 100.00)) as paidPrice,
             false,
             r.rating as rating,
             r.reviewText as reviewText,
             false,
             cp.deliveredDate as deliveredDate
             from CustomerProduct cp
             JOIN cp.customer c
             JOIN Product p
             JOIN c.user u
             JOIN p.seller s
             JOIN Review r ON r.product.id = p.id
             where u.username = ?1
             order by cp.purchaseDate DESC
             """)
    List<OrderDto> getProductsPurchasedByCustomerUsername(String customerUsername, Pageable pageable);


    @Query("""
            select p from Product p join p.seller s where s.id= ?1
            """)
    List<Product> getProductBySellerId(long id);
}
