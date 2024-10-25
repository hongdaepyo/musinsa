package com.dphong.musinsa.common;

import com.dphong.musinsa.model.dto.Money;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.util.Optional;

@Converter
public class MoneyConverter implements AttributeConverter<Money, Long> {

    @Override
    public Long convertToDatabaseColumn(Money money) {
        return Optional.ofNullable(money).orElse(Money.ZERO).amount();
    }

    @Override
    public Money convertToEntityAttribute(Long aLong) {
        return Optional.ofNullable(aLong).map(Money::of).orElse(Money.ZERO);
    }
}
