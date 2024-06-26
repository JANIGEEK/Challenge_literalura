package com.janigeek.literalura.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ConvertData implements IConvertData {
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public <T> T obtainData(String json, Class<T> class1) {
        try {
            return objectMapper.readValue(json, class1);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
