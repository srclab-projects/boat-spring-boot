package xyz.srclab.spring.boot.bean.test.configure

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import xyz.srclab.spring.boot.bean.configure.CommonBeanDefinitionRegistryPostProcessor
import xyz.srclab.spring.boot.bean.configure.CommonBeanPostProcessor

@Configuration
open class Configuration {

    @Bean
    open fun commonBeanDefinitionRegistryPostProcessor(): CommonBeanDefinitionRegistryPostProcessor =
        CommonBeanDefinitionRegistryPostProcessor()

    @Bean
    open fun commonBeanPostProcessor(): CommonBeanPostProcessor =
        CommonBeanPostProcessor()
}