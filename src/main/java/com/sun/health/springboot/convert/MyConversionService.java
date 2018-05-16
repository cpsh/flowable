package com.sun.health.springboot.convert;

import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.TypeDescriptor;

/**
 * Created by 华硕 on 2018-05-16.
 */
public class MyConversionService implements ConversionService {
    @Override
    public boolean canConvert(Class<?> aClass, Class<?> aClass1) {
        return false;
    }

    @Override
    public boolean canConvert(TypeDescriptor typeDescriptor, TypeDescriptor typeDescriptor1) {
        return false;
    }

    @Override
    public <T> T convert(Object o, Class<T> aClass) {
        return null;
    }

    @Override
    public Object convert(Object o, TypeDescriptor typeDescriptor, TypeDescriptor typeDescriptor1) {
        return null;
    }
}
