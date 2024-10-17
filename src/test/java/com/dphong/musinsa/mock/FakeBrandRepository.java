package com.dphong.musinsa.mock;

import com.dphong.musinsa.domain.Brand;
import com.dphong.musinsa.repository.brand.BrandRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

public class FakeBrandRepository implements BrandRepository {

    AtomicLong autoIncrementId = new AtomicLong(1);
    List<Brand> data = new ArrayList<>();

    @Override
    public Brand findByIdOrNull(Long id) {
        return data.stream().filter(brand -> brand.getId().equals(id)).findFirst().orElse(null);
    }

    @Override
    public Brand save(Brand brand) {
        if (brand.getId() == null || brand.getId() == 0) {
            Brand newBrand = Brand.builder()
                    .id(autoIncrementId.getAndIncrement())
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
}
