package com.dphong.musinsa.mock;

import com.dphong.musinsa.domain.Brand;
import com.dphong.musinsa.model.dto.Products;
import com.dphong.musinsa.repository.brand.BrandRepository;
import java.util.Comparator;
import java.util.Objects;

public class FakeBrandRepository extends AbstractFakeRepository<Brand> implements BrandRepository {

    @Override
    public Brand findByIdOrNull(Long id) {
        return data.stream().filter(brand -> brand.getId().equals(id)).findFirst().orElse(null);
    }

    @Override
    public Brand save(Brand brand) {
        if (brand.getId() == null || brand.getId() == 0) {
            Brand newBrand = Brand.builder()
                    .id(autoIncrementId.incrementAndGet())
                    .name(brand.getName())
                    .build();
            data.add(newBrand);
            return newBrand;
        } else {
            data.removeIf(it -> Objects.equals(it.getId(), brand.getId()));
            data.add(brand);
            return brand;
        }
    }

    @Override
    public void delete(Brand brand) {
        data.removeIf(it -> Objects.equals(it.getId(), brand.getId()));
    }

    @Override
    public Brand findBrandWithSumOfLowestPrices() {
        return data.stream()
                .min(Comparator.comparingLong(brand -> new Products(brand.getProducts()).getSumOfPrices()))
                .orElseThrow();
    }
}
