package xyz.srclab.spring.boot.common.bean;

import xyz.srclab.spring.boot.common.object.CommonObjectConverter;
import xyz.srclab.spring.boot.common.object.ObjectConverter;

public class BeanOperatorBuilder {

    public static BeanOperatorBuilder newBuilder() {
        return new BeanOperatorBuilder();
    }

    private BeanResolver beanResolver = CommonBeanResolver.INSTANCE;
    private ObjectConverter objectConverter = CommonObjectConverter.INSTANCE;
    private BeanOperatorStrategy.CopyProperty copyPropertyStrategy = CommonBeanOperatorStrategy.CopyProperty.INSTANCE;

    public BeanOperatorBuilder setBeanResolver(BeanResolver beanResolver) {
        this.beanResolver = beanResolver;
        return this;
    }

    public BeanOperatorBuilder setObjectConverter(ObjectConverter objectConverter) {
        this.objectConverter = objectConverter;
        return this;
    }

    public BeanOperatorBuilder setCopyPropertyStrategy(BeanOperatorStrategy.CopyProperty copyPropertyStrategy) {
        this.copyPropertyStrategy = copyPropertyStrategy;
        return this;
    }

    public BeanOperator build() {
        return new BeanOperatorImpl(this);
    }

    private static class BeanOperatorImpl implements BeanOperator {

        private final BeanResolver beanResolver;
        private final ObjectConverter objectConverter;
        private final BeanOperatorStrategy.CopyProperty copyPropertyStrategy;

        private BeanOperatorImpl(BeanOperatorBuilder builder) {
            this.beanResolver = builder.beanResolver;
            this.objectConverter = builder.objectConverter;
            this.copyPropertyStrategy = builder.copyPropertyStrategy;
        }

        @Override
        public BeanResolver getBeanResolver() {
            return beanResolver;
        }

        @Override
        public ObjectConverter getObjectConverter() {
            return objectConverter;
        }

        @Override
        public BeanOperatorStrategy.CopyProperty getCopyPropertyStrategy() {
            return copyPropertyStrategy;
        }
    }
}
