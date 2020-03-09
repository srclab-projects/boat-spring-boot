package xyz.srclab.spring.boot.bean.scan;

import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.type.filter.TypeFilter;
import xyz.srclab.spring.boot.common.collection.IterableHelper;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class BeanScannerBuilder {

    public static BeanScannerBuilder newBuilder() {
        return new BeanScannerBuilder();
    }

    private static final TypeFilter PASS_ALL = (metadataReader, metadataReaderFactory) -> true;

    private BeanDefinitionRegistry registry;
    private boolean useDefaultFilters = false;
    private List<TypeFilter> includeFilter = new LinkedList<>();
    private List<TypeFilter> excludeFilter = new LinkedList<>();

    public BeanScannerBuilder setBeanDefinitionRegistry(BeanDefinitionRegistry registry) {
        this.registry = registry;
        return this;
    }

    public BeanScannerBuilder useDefaultFilters(boolean useDefaultFilters) {
        this.useDefaultFilters = useDefaultFilters;
        return this;
    }

    public BeanScannerBuilder addIncludeFilter(TypeFilter filter) {
        includeFilter.add(filter);
        return this;
    }

    public BeanScannerBuilder addIncludeFilters(TypeFilter... filters) {
        return addIncludeFilters(Arrays.asList(filters));
    }

    public BeanScannerBuilder addIncludeFilters(Iterable<TypeFilter> filters) {
        includeFilter.addAll(IterableHelper.castCollection(filters));
        return this;
    }

    public BeanScannerBuilder includeAll() {
        includeFilter.add(PASS_ALL);
        return this;
    }

    public BeanScannerBuilder addExcludeFilter(TypeFilter filter) {
        excludeFilter.add(filter);
        return this;
    }

    public BeanScannerBuilder addExcludeFilters(TypeFilter... filters) {
        return addExcludeFilters(Arrays.asList(filters));
    }

    public BeanScannerBuilder addExcludeFilters(Iterable<TypeFilter> filters) {
        excludeFilter.addAll(IterableHelper.castCollection(filters));
        return this;
    }

    public BeanScanner build() {
        return new BeanScannerImpl(this);
    }

    private static class BeanScannerImpl implements BeanScanner {

        private final BeanScannerProvider provider;

        private BeanScannerImpl(BeanScannerBuilder builder) {
            this.provider = new BeanScannerProvider(
                    builder.registry == null ? new GenericApplicationContext() : builder.registry,
                    builder.useDefaultFilters
            );
            for (TypeFilter typeFilter : builder.includeFilter) {
                this.provider.addIncludeFilter(typeFilter);
            }
            for (TypeFilter typeFilter : builder.excludeFilter) {
                this.provider.addExcludeFilter(typeFilter);
            }
        }

        @Override
        public Set<BeanDefinitionHolder> scan(String... basePackages) {
            return provider.doScan(basePackages);
        }
    }

    private static class BeanScannerProvider extends ClassPathBeanDefinitionScanner {

        public BeanScannerProvider(BeanDefinitionRegistry registry, boolean useDefaultFilters) {
            super(registry, useDefaultFilters);
        }

        public Set<BeanDefinitionHolder> doScan(String... basePackages) {
            return super.doScan(basePackages);
        }

        @Override
        protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
            return true;
        }
    }
}
