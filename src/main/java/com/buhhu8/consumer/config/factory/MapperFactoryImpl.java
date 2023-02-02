package com.buhhu8.consumer.config.factory;

import com.google.common.base.Suppliers;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Supplier;

@Primary
@Component
public class MapperFactoryImpl implements MapperFactory {

    private final Supplier<MappersProvider> mappersProviderSupplier;

    public MapperFactoryImpl(ObjectFactory<MappersProvider> mappersProviderBeanFactory) {
        this.mappersProviderSupplier = Suppliers.memoize(mappersProviderBeanFactory::getObject);
    }

    @Override
    public <S, T> T mapTo(S src, Class<T> classToMap) {
        return mappersProviderSupplier.get().mapTo(src, classToMap);
    }

    @Override
    public <S, T> List<T> mapListTo(List<S> src, Class<T> classToMap) {
        return mappersProviderSupplier.get().mapListTo(src, classToMap);
    }
}
