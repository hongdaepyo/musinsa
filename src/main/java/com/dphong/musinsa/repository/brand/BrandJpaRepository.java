package com.dphong.musinsa.repository.brand;

import com.dphong.musinsa.domain.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BrandJpaRepository extends JpaRepository<Brand, Long> {

    @Query("""
            select b
            from Brand b
            inner join (
                select p.brand.id as id, sum(p.price) as priceSum
                from Product p
                group by p.brand
            ) vp on vp.id = b.id
            order by vp.priceSum asc
            limit 1
            """)
    Brand findTopBySumOfLowestPrices();
}
