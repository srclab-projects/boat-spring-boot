package xyz.srclab.spring.boot.autoconfiguration

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import xyz.srclab.spring.boot.core.CoreBean
import xyz.srclab.spring.boot.core.CoreBeanProperties
import xyz.srclab.spring.boot.core.MyBeanDefinitionRegistryPostProcessor

@Configuration
open class CoreAutoConfiguration {

    @ConditionalOnMissingBean
    @Bean
    open fun coreBean(): CoreBean {
        println("build>>>>>>>>>xyz.srclab.spring.boot.core.coreBean")
        return CoreBean()
    }

    @ConfigurationProperties(prefix = "boat.core")
    @Bean
    open fun coreBeanProperties(): CoreBeanProperties {
        return CoreBeanProperties()
    }

    @Bean
    open fun myBeanDefinitionRegistryPostProcessor(): MyBeanDefinitionRegistryPostProcessor {
        return MyBeanDefinitionRegistryPostProcessor()
    }
}