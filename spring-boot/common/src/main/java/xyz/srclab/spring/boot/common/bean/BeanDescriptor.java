package xyz.srclab.spring.boot.common.bean;

import org.springframework.lang.Nullable;

import java.util.Map;

public interface BeanDescriptor {

    Class<?> getType();

    @Nullable
    BeanPropertyDescriptor getPropertyDescriptor(String propertyName);

    Map<String, BeanPropertyDescriptor> getPropertyDescriptors();
}
