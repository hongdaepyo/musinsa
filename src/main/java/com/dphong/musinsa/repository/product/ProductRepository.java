package com.dphong.musinsa.repository.product;

import com.dphong.musinsa.domain.Product;
import com.dphong.musinsa.domain.ProductCategory;
import java.util.List;

public interface ProductRepository {

    Product findByIdOrNull(Long id);
    List<Product> findAllLowestPriceProductsByCategory();
    Product findLowestPriceProductByCategory(ProductCategory category);
    Product findHighestPriceProductByCategory(ProductCategory category);
    void delete(Product product);
    Product save(Product product);
}
