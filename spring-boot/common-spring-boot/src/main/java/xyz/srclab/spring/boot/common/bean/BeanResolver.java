package xyz.srclab.spring.boot.common.bean;

public interface BeanResolver {

    static BeanResolverBuilder newBuilder() {
        return BeanResolverBuilder.newBuilder();
    }

    boolean supportBean(Object bean);

    BeanDescriptor resolve(Object bean);
}
