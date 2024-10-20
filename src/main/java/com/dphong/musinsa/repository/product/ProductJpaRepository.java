package com.dphong.musinsa.repository.product;

import com.dphong.musinsa.domain.Product;
import com.dphong.musinsa.domain.ProductCategory;
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

    @Query("""
            select p
            from Product p
            left join Product p2 on p.category = p2.category and p.brand = p2.brand and p.price > p2.price
            where p2.id is null and p.brand.id = :brandId
            group by p.category
            """)
    List<Product> findAllLowestPriceProductsByBrandId(Long brandId);

    Product findTopByCategoryOrderByPrice(ProductCategory category);
    Product findTopByCategoryOrderByPriceDesc(ProductCategory category);
}
