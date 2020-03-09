package xyz.srclab.spring.boot.common.bean;

import xyz.srclab.spring.boot.common.object.CommonObjectConverter;
import xyz.srclab.spring.boot.common.object.ObjectConverter;

public class CommonBeanOperator implements BeanOperator {

    public static final CommonBeanOperator INSTANCE = new CommonBeanOperator();

    @Override
    public BeanResolver getBeanResolver() {
        return CommonBeanResolver.INSTANCE;
    }

    @Override
    public ObjectConverter getObjectConverter() {
        return CommonObjectConverter.INSTANCE;
    }
}
