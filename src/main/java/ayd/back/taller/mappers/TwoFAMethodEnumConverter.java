package ayd.back.taller.mappers;

import ayd.back.taller.repository.enums.TwofaMethodEnum;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.Objects;

@Converter(autoApply = true)
public class TwoFAMethodEnumConverter implements AttributeConverter<TwofaMethodEnum, String> {

    @Override
    public String convertToDatabaseColumn(TwofaMethodEnum attribute) {
        if (Objects.isNull(attribute)) return null;
        return attribute.name().toLowerCase();
    }

    @Override
    public TwofaMethodEnum convertToEntityAttribute(String dbData) {
        if (Objects.isNull(dbData)) return null;
        return TwofaMethodEnum.valueOf(dbData.toUpperCase());
    }
}
