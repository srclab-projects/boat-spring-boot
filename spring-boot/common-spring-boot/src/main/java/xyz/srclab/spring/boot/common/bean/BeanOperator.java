package xyz.srclab.spring.boot.common.bean;

import xyz.srclab.spring.boot.common.object.ObjectConverter;

import java.util.Map;

public interface BeanOperator {

    static BeanOperatorBuilder newBuilder() {
        return BeanOperatorBuilder.newBuilder();
    }

    BeanResolver getBeanResolver();

    ObjectConverter getObjectConverter();

    default void copyProperties(Object source, Object dest) {
        if (source instanceof Map && dest instanceof Map) {
            ((Map) source).forEach((k, v) -> {
                Object value = getObjectConverter().convert(v, Object.class);
                if (value == ObjectConverter.NO_CONVERSION) {
                    return;
                }
                ((Map) dest).put(k, value);
            });
        } else if (source instanceof Map) {
            BeanDescriptor beanDescriptor = getBeanResolver().resolve(dest);
            ((Map) source).forEach((k, v) -> {
                String name = String.valueOf(k);
                BeanPropertyDescriptor propertyDescriptor = beanDescriptor.getPropertyDescriptor(name);
                if (propertyDescriptor != null && propertyDescriptor.isWriteable()) {
                    Class<?> type = propertyDescriptor.getType();
                    Object value = getObjectConverter().convert(v, type);
                    if (value == ObjectConverter.NO_CONVERSION) {
                        return;
                    }
                    propertyDescriptor.setValue(dest, value);
                }
            });
        } else if (dest instanceof Map) {
            BeanDescriptor beanDescriptor = getBeanResolver().resolve(source);
            Map<String, BeanPropertyDescriptor> propertyDescriptorMap = beanDescriptor.getPropertyDescriptors();
            propertyDescriptorMap.forEach((name, descriptor) -> {
                Object sourceValue = descriptor.getValue(source);
                Object destValue = getObjectConverter().convert(sourceValue, Object.class);
                if (destValue == ObjectConverter.NO_CONVERSION) {
                    return;
                }
                ((Map) dest).put(name, destValue);
            });
        } else {
            BeanDescriptor sourceDescriptor = getBeanResolver().resolve(source);
            BeanDescriptor destDescriptor = getBeanResolver().resolve(dest);
            Map<String, BeanPropertyDescriptor> sourcePropertyDescriptorMap = sourceDescriptor.getPropertyDescriptors();
            sourcePropertyDescriptorMap.forEach((name, descriptor) -> {
                BeanPropertyDescriptor destPropertyDescriptor = destDescriptor.getPropertyDescriptor(name);
                if (destPropertyDescriptor != null && destPropertyDescriptor.isWriteable()) {
                    Class<?> type = destPropertyDescriptor.getType();
                    Object sourceValue = descriptor.getValue(source);
                    Object destValue = getObjectConverter().convert(sourceValue, type);
                    if (destValue == ObjectConverter.NO_CONVERSION) {
                        return;
                    }
                    destPropertyDescriptor.setValue(dest, destValue);
                }
            });
        }
    }
}
