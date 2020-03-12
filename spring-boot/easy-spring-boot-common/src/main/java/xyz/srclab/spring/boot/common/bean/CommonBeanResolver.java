package xyz.srclab.spring.boot.common.bean;

import java.util.Map;

public class CommonBeanResolver implements BeanResolver {

    public static final CommonBeanResolver INSTANCE = new CommonBeanResolver();

    @Override
    public boolean supportBean(Object bean) {
        return true;
    }

    @Override
    public BeanDescriptor resolve(Object bean) {
        return bean instanceof Map ?
                MapAsBeanResolver.INSTANCE.resolve(bean)
                :
                PojoBeanResolver.INSTANCE.resolve(bean);
    }
}