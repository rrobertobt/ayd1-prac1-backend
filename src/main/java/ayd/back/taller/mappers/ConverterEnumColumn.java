package ayd.back.taller.mappers;


import ayd.back.taller.repository.enums.UserRoleEnum;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.Objects;

@Converter(autoApply = true)
public class ConverterEnumColumn implements AttributeConverter<UserRoleEnum, String> {


    @Override
    public String convertToDatabaseColumn(UserRoleEnum attribute) {
        if (Objects.isNull(attribute)) return null;
        return attribute.name().toLowerCase();
    }

    @Override
    public UserRoleEnum convertToEntityAttribute(String dbData) {
        if (Objects.isNull(dbData)) return null;
        return UserRoleEnum.valueOf(dbData.toUpperCase());
    }

}
