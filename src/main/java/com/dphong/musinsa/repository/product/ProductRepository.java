package com.dphong.musinsa.repository.product;

import com.dphong.musinsa.domain.Product;
import com.dphong.musinsa.domain.ProductCategory;
import java.util.List;

public interface ProductRepository {

    List<Product> findAllLowestPriceProductsByCategory();
    List<Product> findAllLowestPriceProductsByBrandId(Long brandId);
    Product findLowestPriceProductByCategory(ProductCategory category);
    Product findHighestPriceProductByCategory(ProductCategory category);
    Product save(Product product);
}
