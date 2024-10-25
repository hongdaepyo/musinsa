package com.dphong.musinsa.common;

import com.dphong.musinsa.model.dto.Money;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import java.io.IOException;
import java.text.NumberFormat;

public class FormattedMoneySerializer extends ToStringSerializer {

    @Override
    public void serialize(Object value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        Money money = (Money) value;
        String formatted = NumberFormat.getInstance().format(money.amount());
        gen.writeString(formatted);
    }
}
