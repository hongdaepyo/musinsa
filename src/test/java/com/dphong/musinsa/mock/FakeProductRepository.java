package com.dphong.musinsa.mock;

import com.dphong.musinsa.domain.Product;
import com.dphong.musinsa.domain.ProductCategory;
import com.dphong.musinsa.repository.product.ProductRepository;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

public class FakeProductRepository implements ProductRepository {

    AtomicLong autoIncrementId = new AtomicLong(1);
    List<Product> data = new ArrayList<>();


    @Override
    public Product findByIdOrNull(Long id) {
        return data.stream().filter(product -> product.getId().equals(id)).findFirst().orElse(null);
    }

    @Override
    public List<Product> findAllLowestPriceProductsByCategory() {
        return data.stream()
                .collect(Collectors.groupingBy(Product::getCategory, Collectors.minBy(Comparator.comparingInt(Product::getPrice))))
                .values().stream()
                .filter(Optional::isPresent)
                .map(Optional::get)
                .sorted(Comparator.comparingInt(product -> product.getCategory().getOrder()))
                .toList();
    }

    @Override
    public Product findLowestPriceProductByCategory(ProductCategory category) {
        return data.stream()
                .filter(product -> product.getCategory() == category)
                .min(Comparator.comparingInt(Product::getPrice))
                .orElseThrow();
    }

    @Override
    public Product findHighestPriceProductByCategory(ProductCategory category) {
        return data.stream()
                .filter(product -> product.getCategory() == category)
                .max(Comparator.comparingInt(Product::getPrice))
                .orElseThrow();
    }

    @Override
    public void delete(Product product) {
        data.removeIf(it -> Objects.equals(it.getId(), product.getId()));
    }

    @Override
    public Product save(Product product) {
        if (product.getId() == null || product.getId() == 0) {
            Product newProduct = Product.builder()
                    .id(autoIncrementId.getAndIncrement())
                    .price(product.getPrice())
                    .name(product.getName())
                    .brand(product.getBrand())
                    .category(product.getCategory())
                    .build();
            data.add(newProduct);
            return newProduct;
        } else {
            data.removeIf(it -> Objects.equals(it.getId(), product.getId()));
            data.add(product);
            return product;
        }
    }
}
