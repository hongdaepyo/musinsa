package com.dphong.musinsa.repository.brand;

import com.dphong.musinsa.domain.Brand;

public interface BrandRepository {

    Brand findByIdOrNull(Long id);
    Brand save(Brand brand);
}
