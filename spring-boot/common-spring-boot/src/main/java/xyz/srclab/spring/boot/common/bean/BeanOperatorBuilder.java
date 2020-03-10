package xyz.srclab.spring.boot.common.bean;

import xyz.srclab.spring.boot.common.object.ObjectConverter;

public class BeanOperatorBuilder {

    public static BeanOperatorBuilder newBuilder() {
        return new BeanOperatorBuilder();
    }

    private BeanResolver beanResolver;
    private ObjectConverter objectConverter;

    public BeanOperatorBuilder setBeanResolver(BeanResolver beanResolver) {
        this.beanResolver = beanResolver;
        return this;
    }

    public BeanOperatorBuilder setObjectConverter(ObjectConverter objectConverter) {
        this.objectConverter = objectConverter;
        return this;
    }

    public BeanOperator build() {
        return new BeanOperatorImpl(beanResolver, objectConverter);
    }

    private static class BeanOperatorImpl implements BeanOperator {

        private final BeanResolver beanResolver;
        private final ObjectConverter objectConverter;

        private BeanOperatorImpl(BeanResolver beanResolver, ObjectConverter objectConverter) {
            this.beanResolver = beanResolver;
            this.objectConverter = objectConverter;
        }

        @Override
        public BeanResolver getBeanResolver() {
            return beanResolver;
        }

        @Override
        public ObjectConverter getObjectConverter() {
            return objectConverter;
        }
    }
}
