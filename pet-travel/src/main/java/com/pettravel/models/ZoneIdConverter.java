package com.pettravel.models;

import java.time.ZoneId;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class ZoneIdConverter implements AttributeConverter<ZoneId, String> {

    @Override
    public String convertToDatabaseColumn(ZoneId zoneId) {
        return zoneId == null ? null : zoneId.getId();
    }

    @Override
    public ZoneId convertToEntityAttribute(String zoneId) {
        return zoneId == null ? null : ZoneId.of(zoneId);
    }
}
