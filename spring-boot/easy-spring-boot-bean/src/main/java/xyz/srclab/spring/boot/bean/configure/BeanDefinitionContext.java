package xyz.srclab.spring.boot.bean.configure;

import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;

public interface BeanDefinitionContext {

    ApplicationContext getApplicationContext();

    BeanDefinitionRegistry getRegistry();

    Environment getEnvironment();

    AutowireCapableBeanFactory getAutowireCapableBeanFactory();
}
