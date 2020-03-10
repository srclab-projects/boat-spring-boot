package xyz.srclab.spring.boot.common.bean;

public interface BeanPropertyConverter<T> {

    boolean supportBean(Object bean);

    T convert(Object bean);
}
