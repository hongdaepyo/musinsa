package com.dphong.musinsa.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.dphong.musinsa.IntegrationTest;
import com.dphong.musinsa.domain.Brand;
import com.dphong.musinsa.model.request.brand.BrandCreateRequest;
import com.dphong.musinsa.model.request.brand.BrandUpdateRequest;
import com.dphong.musinsa.repository.brand.BrandJpaRepository;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;


class BrandControllerIntegrationTest extends IntegrationTest {

    @Autowired
    private BrandJpaRepository brandJpaRepository;

    @Test
    void 브랜드를_추가한다() throws Exception {
        // given
        BrandCreateRequest request = new BrandCreateRequest("Musinsa Brand");

        // when
        // then
        mockMvc.perform(
                post("/v1/brands")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        ).andExpect(status().isOk());

        List<Brand> brands = brandJpaRepository.findAll();
        assertThat(brands).hasSize(1);
    }

    @Test
    void 브랜드를_수정한다() throws Exception {
        // given
        Brand brand = brandJpaRepository.save(Brand.builder().name("testBrand").build());
        BrandUpdateRequest request = new BrandUpdateRequest("Musinsa Brand");

        // when
        // then
        mockMvc.perform(
                put("/v1/brands/%s".formatted(brand.getId()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        ).andExpect(status().isOk());

        Brand updatedBrand = brandJpaRepository.findAll().getFirst();
        assertThat(updatedBrand.getName()).isEqualTo("Musinsa Brand");
    }

    @Test
    void 브랜드를_삭제한다() throws Exception {
        // given
        Brand brand = brandJpaRepository.save(Brand.builder().name("testBrand").build());

        // when
        // then
        mockMvc.perform(
                delete("/v1/brands/%s".formatted(brand.getId()))
        ).andExpect(status().isOk());

        List<Brand> brands = brandJpaRepository.findAll();
        assertThat(brands).isEmpty();
    }
}
