package com.janigeek.literalura.service;

public interface IConvertData {
    <T> T obtainData(String json, Class<T> class1);
}
