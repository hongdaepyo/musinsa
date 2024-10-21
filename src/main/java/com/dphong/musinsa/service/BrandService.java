package com.dphong.musinsa.service;

import com.dphong.musinsa.common.exception.ResourceNotFoundException;
import com.dphong.musinsa.domain.Brand;
import com.dphong.musinsa.model.dto.BrandUpdateStatus;
import com.dphong.musinsa.model.dto.CommonStatus;
import com.dphong.musinsa.model.request.brand.BrandCreateRequest;
import com.dphong.musinsa.model.request.brand.BrandUpdateRequest;
import com.dphong.musinsa.model.response.brand.BrandCreateResponse;
import com.dphong.musinsa.model.response.brand.BrandDeleteResponse;
import com.dphong.musinsa.model.response.brand.BrandUpdateResponse;
import com.dphong.musinsa.repository.brand.BrandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BrandService {

    private final BrandRepository brandRepository;

    public BrandCreateResponse create(BrandCreateRequest request) {
        Brand brand = brandRepository.save(Brand.builder().name(request.name()).build());
        return new BrandCreateResponse(brand.getId(), brand.getName());
    }

    public BrandUpdateResponse update(Long id, BrandUpdateRequest request) {
        Brand brand = brandRepository.findByIdOrNull(id);
        if (brand == null) {
            throw new ResourceNotFoundException("Brand", id);
        }
        brand.update(request.name());
        brandRepository.save(brand);
        return new BrandUpdateResponse(brand.getId(), BrandUpdateStatus.SUCCESS);
    }

    public BrandDeleteResponse delete(Long id) {
        Brand brand = brandRepository.findByIdOrNull(id);
        if (brand == null) {
            throw new ResourceNotFoundException("Brand", id);
        }
        brandRepository.delete(brand);
        return new BrandDeleteResponse(id, CommonStatus.SUCCESS);
    }
}
