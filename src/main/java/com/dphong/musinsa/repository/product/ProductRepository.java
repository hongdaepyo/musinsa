package com.dphong.musinsa.repository.product;

import com.dphong.musinsa.domain.Product;
import java.util.List;

public interface ProductRepository {

    List<Product> findAllLowestPriceProductsByCategory();
    List<Product> findAllLowestPriceProductsByBrandId(Long brandId);
    Product save(Product product);
}
