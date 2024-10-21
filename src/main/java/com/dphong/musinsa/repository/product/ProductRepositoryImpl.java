package com.dphong.musinsa.repository.product;

import com.dphong.musinsa.domain.Product;
import com.dphong.musinsa.domain.ProductCategory;
import java.util.Comparator;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepository {

    private final ProductJpaRepository productJpaRepository;

    @Override
    public Product findByIdOrNull(Long id) {
        return productJpaRepository.findById(id).orElse(null);
    }

    @Override
    public List<Product> findAllLowestPriceProductsByCategory() {
        return productJpaRepository.findAllLowestPriceProductsByCategory().stream()
                .sorted(Comparator.comparingInt(product -> product.getCategory().getOrder()))
                .toList();
    }

    @Override
    public Product findLowestPriceProductByCategory(ProductCategory category) {
        return productJpaRepository.findTopByCategoryOrderByPrice(category);
    }

    @Override
    public Product findHighestPriceProductByCategory(ProductCategory category) {
        return productJpaRepository.findTopByCategoryOrderByPriceDesc(category);
    }

    @Override
    public void delete(Product product) {
        productJpaRepository.delete(product);
    }

    @Override
    public Product save(Product product) {
        return productJpaRepository.save(product);
    }
}
