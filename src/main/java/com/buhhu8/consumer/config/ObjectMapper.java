package com.buhhu8.consumer.config;

public interface ObjectMapper <S, T> {

    T convert(S src);
}
