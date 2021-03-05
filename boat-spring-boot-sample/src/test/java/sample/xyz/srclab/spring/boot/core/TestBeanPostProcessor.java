package sample.xyz.srclab.spring.boot.core;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeansException;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.stereotype.Component;
import xyz.srclab.spring.boot.bean.SpringBeanPostProcessor;

import java.util.LinkedList;
import java.util.List;

@Component
public class TestBeanPostProcessor implements SpringBeanPostProcessor {

    private List<String> sequence = new LinkedList<>();

    private boolean isPostProcessBeanDefinitionRegistry = false;
    private boolean isPostProcessBeanFactory = false;
    private boolean isPostProcessBeforeInstantiation = false;
    private boolean isPostProcessAfterInstantiation = false;
    private boolean isPostProcessBeforeInitialization = false;
    private boolean isPostProcessAfterInitialization = false;
    private boolean isPostProcessProperties = false;

    @Override
    public void postProcessBeanDefinitionRegistry(@NotNull BeanDefinitionRegistry registry) {
        if (!isPostProcessBeanDefinitionRegistry) {
            sequence.add("postProcessBeanDefinitionRegistry");
            isPostProcessBeanDefinitionRegistry = true;
        }
    }

    @Override
    public void postProcessBeanFactory(@NotNull ConfigurableListableBeanFactory beanFactory) {
        if (!isPostProcessBeanFactory) {
            sequence.add("postProcessBeanFactory");
            isPostProcessBeanFactory = true;
        }
    }

    @Override
    public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
        if (!isPostProcessBeforeInstantiation) {
            sequence.add("postProcessBeforeInstantiation");
            isPostProcessBeforeInstantiation = true;
        }
        return null;
    }

    @Override
    public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
        if (!isPostProcessAfterInstantiation) {
            sequence.add("postProcessAfterInstantiation");
            isPostProcessAfterInstantiation = true;
        }
        return true;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (!isPostProcessBeforeInitialization) {
            sequence.add("postProcessBeforeInitialization");
            isPostProcessBeforeInitialization = true;
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (!isPostProcessAfterInitialization) {
            sequence.add("postProcessAfterInitialization");
            isPostProcessAfterInitialization = true;
        }
        return null;
    }

    @Override
    public PropertyValues postProcessProperties(PropertyValues pvs, Object bean, String beanName) throws BeansException {
        if (!isPostProcessProperties) {
            sequence.add("postProcessProperties");
            isPostProcessProperties = true;
        }
        return null;
    }

    public List<String> getSequence() {
        return sequence;
    }
}
