package com.dphong.musinsa.model.dto;

import com.dphong.musinsa.common.FormattedMoneySerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(using = FormattedMoneySerializer.class)
public record Money(
        long amount
) {

    public static Money ZERO = new Money(0L);

    public static Money of(long amount) {
        return new Money(amount);
    }

    public static Money of(int amount) {
        return new Money(amount);
    }
}
