package xyz.srclab.spring.boot.common.bean;

import xyz.srclab.spring.boot.common.object.ObjectConverter;

import java.util.Map;

public interface BeanOperator {

    static BeanOperatorBuilder newBuilder() {
        return BeanOperatorBuilder.newBuilder();
    }

    BeanResolver getBeanResolver();

    ObjectConverter getObjectConverter();

    BeanOperatorStrategy.CopyProperty getCopyPropertyStrategy();

    default void copyProperties(Object source, Object dest) {
        BeanDescriptor sourceDescriptor = getBeanResolver().resolve(source);
        BeanDescriptor destDescriptor = getBeanResolver().resolve(dest);
        Map<String, BeanPropertyDescriptor> sourcePropertyDescriptorMap = sourceDescriptor.getPropertyDescriptors();
        sourcePropertyDescriptorMap.forEach((name, descriptor) -> {
            BeanPropertyDescriptor destPropertyDescriptor = destDescriptor.getPropertyDescriptor(name);
            if (destPropertyDescriptor == null) {
                return;
            }
            getCopyPropertyStrategy().copyProperty(
                    descriptor, source, destPropertyDescriptor, dest, getObjectConverter());
        });
    }
}
