package com.buhhu8.consumer.config.factory;

import com.buhhu8.consumer.config.ObjectMapper;
import org.springframework.aop.support.AopUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;

import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Optional.ofNullable;

@Component
public class MappersProvider implements MapperFactory {
    private final Map<Class<?>, MapperToTarget> mappersBySrc;

    public MappersProvider(List<ObjectMapper<?, ?>> mappers) {
        this.mappersBySrc = new HashMap<>();

        mappers.forEach(mapper -> Stream.of(mapper.getClass().getMethods())
            .filter(m -> "convert".equals(m.getName()))
            .filter(m -> !Object.class.equals(m.getReturnType()))
            .findAny()
            .ifPresent(method -> {
                var srcClass = method.getParameters()[0].getType();
                var trgClass = method.getReturnType();
                mappersBySrc.computeIfPresent(srcClass, (k, v) -> v.addMapper(mapper, trgClass));
                mappersBySrc.putIfAbsent(srcClass, new MapperToTarget().addMapper(mapper, trgClass));
            }));
    }

    @Override
    public <S, T> T mapTo(S src, Class<T> classToMap) {
        if (Objects.isNull(src)) {
            return null;
        }
        var originalClass = getOriginalClass(src.getClass());
        @SuppressWarnings("unchecked")
        T result = (T) ofNullable(mappersBySrc.get(originalClass))
            .orElseThrow(() -> new RuntimeException(originalClass.getName()))
            .getMapper(classToMap)
            .orElseThrow(() -> new RuntimeException(src.getClass().getName()))
            .convert(src);

        return result;
    }

    @Override
    public <S, T> List<T> mapListTo(List<S> src, Class<T> classToMap) {
        if (src == null || src.isEmpty()) {
            return new ArrayList<>();
        }
        var srcClass = src.iterator().next().getClass();
        var mapper = mappersBySrc.get(getOriginalClass(srcClass))
            .getMapper(classToMap)
            .orElseThrow(() -> new RuntimeException(srcClass.getName()));

        return src.stream().map(elem -> (T) mapper.convert(elem)).collect(Collectors.toList());
    }

    private Class<?> getOriginalClass(Class<?> sourceClazz) {
        Class<?> result = sourceClazz;
        if (AopUtils.isAopProxy(sourceClazz) || Proxy.isProxyClass(sourceClazz)) {
            result = Stream.of(sourceClazz.getInterfaces())
                .filter(i -> i.getPackageName().startsWith("ru.sberbank.tib"))
                .findFirst()
                .orElse(ClassUtils.getUserClass(sourceClazz));
        } else {
            result = ClassUtils.getUserClass(sourceClazz);
        }
        return result;
    }

    private static final class MapperToTarget {
        private final Map<Class<?>, ObjectMapper<?, ?>> mapperByTarget;

        private MapperToTarget() {
            this.mapperByTarget = new HashMap<>();
        }

        private MapperToTarget addMapper(ObjectMapper<?, ?> mapper, Class<?> trgClass) {
            mapperByTarget.putIfAbsent(trgClass, mapper);
            return this;
        }

        private <S, T> Optional<ObjectMapper<S, T>> getMapper(Class<T> cls) {
            return ofNullable((ObjectMapper<S, T>) mapperByTarget.get(cls));
        }
    }

}