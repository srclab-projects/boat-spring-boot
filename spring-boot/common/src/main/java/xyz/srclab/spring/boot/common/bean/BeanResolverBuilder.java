package xyz.srclab.spring.boot.common.bean;

import java.util.Arrays;
import java.util.LinkedList;

public class BeanResolverBuilder {

    public static BeanResolverBuilder newBuilder() {
        return new BeanResolverBuilder();
    }

    private final LinkedList<BeanResolver> resolvers = new LinkedList<>();

    public BeanResolverBuilder() {
    }

    public BeanResolverBuilder addResolver(BeanResolver resolver) {
        resolvers.addFirst(resolver);
        return this;
    }

    public BeanResolverBuilder addResolvers(BeanResolver... resolvers) {
        return addResolvers(Arrays.asList(resolvers));
    }

    public BeanResolverBuilder addResolvers(Iterable<BeanResolver> resolvers) {
        for (BeanResolver beanResolver : resolvers) {
            this.resolvers.addFirst(beanResolver);
        }
        return this;
    }

    public BeanResolver build() {
        return new BeanResolverImpl(resolvers.toArray(new BeanResolver[0]));
    }

    private static class BeanResolverImpl implements BeanResolver {

        private final BeanResolver[] resolvers;

        private BeanResolverImpl(BeanResolver[] resolvers) {
            this.resolvers = resolvers;
        }

        @Override
        public boolean supportBean(Object bean) {
            for (BeanResolver resolver : resolvers) {
                if (resolver.supportBean(bean)) {
                    return true;
                }
            }
            return false;
        }

        @Override
        public BeanDescriptor resolve(Object bean) {
            for (BeanResolver resolver : resolvers) {
                if (resolver.supportBean(bean)) {
                    return resolver.resolve(bean);
                }
            }
            throw new UnsupportedOperationException("Cannot resolve this bean: " + bean);
        }
    }
}
