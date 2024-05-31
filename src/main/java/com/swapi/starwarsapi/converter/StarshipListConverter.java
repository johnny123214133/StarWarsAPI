package com.swapi.starwarsapi.converter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.lang.reflect.Type;
import java.util.List;

@Converter(autoApply = true)
public class StarshipListConverter implements AttributeConverter<List<Integer>, String> {

    Gson gson = new Gson();
    Type listType = new TypeToken<List<Integer>>() {}.getType();

    @Override
    public String convertToDatabaseColumn(List<Integer> integers) {
        return gson.toJson(integers);
    }

    @Override
    public List<Integer> convertToEntityAttribute(String s) {
        return gson.fromJson(s, listType);
    }
}
