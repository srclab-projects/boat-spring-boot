package xyz.srclab.spring.boot.bean.configure;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;

import java.util.Collection;

public class CommonBeanDefinitionRegistryPostProcessor implements
        BeanDefinitionRegistryPostProcessor, ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        //Do nothing.
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        DefaultListableBeanFactory context = initApplicationContext(beanFactory);

        Collection<BeanConfigure> beanConfigures = context.getBeansOfType(BeanConfigure.class).values();
        if (beanConfigures.isEmpty()) {
            return;
        }
        Environment environment = new EnvironmentProxy(applicationContext);
        for (BeanConfigure beanConfigure : beanConfigures) {
            beanConfigure.configureBeanDefinition(new BeanDefinitionContextImpl(
                    applicationContext, context, environment, context));
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    private DefaultListableBeanFactory initApplicationContext(Object any) {
        if (any instanceof DefaultListableBeanFactory) {
            return (DefaultListableBeanFactory) any;
        }
        throw new IllegalStateException("Initialize application context failed!");
    }

    private static class BeanDefinitionContextImpl implements BeanDefinitionContext {

        private final ApplicationContext applicationContext;
        private final BeanDefinitionRegistry registry;
        private final Environment environment;
        private final AutowireCapableBeanFactory autowireCapableBeanFactory;

        private BeanDefinitionContextImpl(
                ApplicationContext applicationContext,
                BeanDefinitionRegistry registry,
                Environment environment,
                AutowireCapableBeanFactory autowireCapableBeanFactory
        ) {
            this.applicationContext = applicationContext;
            this.registry = registry;
            this.environment = environment;
            this.autowireCapableBeanFactory = autowireCapableBeanFactory;
        }

        @Override
        public ApplicationContext getApplicationContext() {
            return applicationContext;
        }

        @Override
        public BeanDefinitionRegistry getRegistry() {
            return registry;
        }

        @Override
        public Environment getEnvironment() {
            return environment;
        }

        @Override
        public AutowireCapableBeanFactory getAutowireCapableBeanFactory() {
            return autowireCapableBeanFactory;
        }
    }

    private static class EnvironmentProxy implements Environment {

        private final ApplicationContext context;
        private Environment environment;

        private EnvironmentProxy(ApplicationContext context) {
            this.context = context;
        }

        @Override
        public String[] getActiveProfiles() {
            return getEnvironment().getActiveProfiles();
        }

        @Override
        public String[] getDefaultProfiles() {
            return getEnvironment().getDefaultProfiles();
        }

        @Override
        public boolean acceptsProfiles(String... profiles) {
            return getEnvironment().acceptsProfiles(profiles);
        }

        @Override
        public boolean acceptsProfiles(Profiles profiles) {
            return getEnvironment().acceptsProfiles(profiles);
        }

        @Override
        public boolean containsProperty(String key) {
            return getEnvironment().containsProperty(key);
        }

        @Override
        public String getProperty(String key) {
            return getEnvironment().getProperty(key);
        }

        @Override
        public String getProperty(String key, String defaultValue) {
            return getEnvironment().getProperty(key, defaultValue);
        }

        @Override
        public <T> T getProperty(String key, Class<T> targetType) {
            return getEnvironment().getProperty(key, targetType);
        }

        @Override
        public <T> T getProperty(String key, Class<T> targetType, T defaultValue) {
            return getEnvironment().getProperty(key, targetType, defaultValue);
        }

        @Override
        public String getRequiredProperty(String key) throws IllegalStateException {
            return getEnvironment().getRequiredProperty(key);
        }

        @Override
        public <T> T getRequiredProperty(String key, Class<T> targetType) throws IllegalStateException {
            return getEnvironment().getRequiredProperty(key, targetType);
        }


        @Override
        public String resolvePlaceholders(String text) {
            return getEnvironment().resolvePlaceholders(text);
        }

        @Override
        public String resolveRequiredPlaceholders(String text) throws IllegalArgumentException {
            return getEnvironment().resolveRequiredPlaceholders(text);
        }

        private Environment getEnvironment() {
            if (environment == null) {
                environment = context.getEnvironment();
            }
            return environment;
        }
    }
}
