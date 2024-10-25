package com.dphong.musinsa.domain;

import com.dphong.musinsa.common.MoneyConverter;
import com.dphong.musinsa.model.dto.Money;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Enumerated(EnumType.STRING)
    private ProductCategory category;

    @Convert(converter = MoneyConverter.class)
    private Money price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brandId")
    private Brand brand;

    @Builder
    public Product(Long id, String name, ProductCategory category, Money price, Brand brand) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.price = price;
        this.brand = brand;
    }

    public void update(String name, ProductCategory category, Money price) {
        this.name = name;
        this.category = category;
        this.price = price;
    }
}
