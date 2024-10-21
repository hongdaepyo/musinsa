package com.dphong.musinsa.repository.brand;

import com.dphong.musinsa.domain.Brand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class BrandRepositoryImpl implements BrandRepository {

    private final BrandJpaRepository brandJpaRepository;

    @Override
    public Brand findByIdOrNull(Long id) {
        return brandJpaRepository.findById(id).orElse(null);
    }

    @Override
    public Brand save(Brand brand) {
        return brandJpaRepository.save(brand);
    }

    @Override
    public void delete(Brand brand) {
        brandJpaRepository.delete(brand);
    }
}
