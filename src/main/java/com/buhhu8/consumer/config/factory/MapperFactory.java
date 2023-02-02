package com.buhhu8.consumer.config.factory;

import java.util.List;

public interface MapperFactory {

    <S, T> T mapTo(S src, Class<T> classToMap);

    <S, T> List<T> mapListTo(List<S> src, Class<T> classToMap);
}
