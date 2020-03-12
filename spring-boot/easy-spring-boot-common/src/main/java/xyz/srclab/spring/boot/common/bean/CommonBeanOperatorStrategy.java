package xyz.srclab.spring.boot.common.bean;

import xyz.srclab.spring.boot.common.object.ObjectConverter;

public class CommonBeanOperatorStrategy implements BeanOperatorStrategy {

    public static class CopyProperty implements BeanOperatorStrategy.CopyProperty {

        public static final CopyProperty INSTANCE = new CopyProperty();

        public void copyProperty(
                BeanPropertyDescriptor sourceProperty, Object sourceBean,
                BeanPropertyDescriptor destProperty, Object destBean,
                ObjectConverter objectConverter) {
            if (!sourceProperty.isReadable() || !destProperty.isWriteable()) {
                return;
            }
            Object sourceValue = sourceProperty.getValue(sourceBean);
            Class<?> type = destProperty.getType();
            Object destValue = objectConverter.convert(sourceValue, type);
            destProperty.setValue(destBean, destValue);
        }
    }

    public static class CopyPropertyIgnoreNull implements BeanOperatorStrategy.CopyProperty {

        public static final CopyPropertyIgnoreNull INSTANCE = new CopyPropertyIgnoreNull();

        public void copyProperty(
                BeanPropertyDescriptor sourceProperty, Object sourceBean,
                BeanPropertyDescriptor destProperty, Object destBean,
                ObjectConverter objectConverter) {
            if (!sourceProperty.isReadable() || !destProperty.isWriteable()) {
                return;
            }
            Object sourceValue = sourceProperty.getValue(sourceBean);
            if (sourceValue == null) {
                return;
            }
            Class<?> type = destProperty.getType();
            Object destValue = objectConverter.convert(sourceValue, type);
            destProperty.setValue(destBean, destValue);
        }
    }
}
