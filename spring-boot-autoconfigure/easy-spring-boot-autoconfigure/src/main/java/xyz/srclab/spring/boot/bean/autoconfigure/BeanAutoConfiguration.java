package xyz.srclab.spring.boot.bean.autoconfigure;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import xyz.srclab.spring.boot.bean.configure.CommonBeanDefinitionRegistryPostProcessor;
import xyz.srclab.spring.boot.bean.configure.CommonBeanPostProcessor;

@ConditionalOnMissingBean(BeanAutoConfiguration.class)
@ConditionalOnClass({
        CommonBeanDefinitionRegistryPostProcessor.class,
        CommonBeanPostProcessor.class
})
public class BeanAutoConfiguration {

    @ConditionalOnMissingBean(CommonBeanDefinitionRegistryPostProcessor.class)
    @Bean
    public CommonBeanDefinitionRegistryPostProcessor commonBeanDefinitionRegistryPostProcessor() {
        return new CommonBeanDefinitionRegistryPostProcessor();
    }

    @ConditionalOnMissingBean(CommonBeanPostProcessor.class)
    @Bean
    public CommonBeanPostProcessor commonBeanPostProcessor() {
        return new CommonBeanPostProcessor();
    }
}
