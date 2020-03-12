package xyz.srclab.spring.boot.common.bean;

import xyz.srclab.spring.boot.common.object.ObjectConverter;

public interface BeanOperatorStrategy {

    interface CopyProperty {

        void copyProperty(
                BeanPropertyDescriptor sourceProperty, Object sourceBean,
                BeanPropertyDescriptor destProperty, Object destBean,
                ObjectConverter objectConverter);
    }
}
