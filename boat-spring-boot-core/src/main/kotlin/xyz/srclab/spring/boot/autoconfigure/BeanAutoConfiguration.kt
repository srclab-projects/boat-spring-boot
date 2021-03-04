package xyz.srclab.spring.boot.autoconfigure

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import xyz.srclab.spring.boot.bean.SpringBeanManager

@Configuration
open class BeanAutoConfiguration {

    @ConditionalOnMissingBean
    @Bean
    open fun springBeanManager(): SpringBeanManager {
        return SpringBeanManager()
    }
}