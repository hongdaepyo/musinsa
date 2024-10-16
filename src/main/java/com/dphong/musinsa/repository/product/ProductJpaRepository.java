package com.dphong.musinsa.repository.product;

import com.dphong.musinsa.domain.Product;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductJpaRepository extends JpaRepository<Product, Long> {

    @Query("""
            select p
            from Product p
            left join Product p2 on p.category = p2.category and p.price > p2.price
            where p2.id is null
            group by p.category
            """)
    List<Product> findAllLowestPriceProductsByCategory();
}
