package xyz.srclab.spring.boot.autoconfigure

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import xyz.srclab.spring.boot.core.CoreProperties
import xyz.srclab.spring.boot.core.GreetingBean

@Configuration
open class CoreAutoConfiguration {

    @ConfigurationProperties(prefix = "boat.core")
    @ConditionalOnMissingBean
    @Bean("xyz.srclab.spring.boot.core.CoreProperties")
    open fun coreProperties(): CoreProperties {
        return CoreProperties()
    }

    @ConditionalOnMissingBean
    @Bean("xyz.srclab.spring.boot.core.GreetingBean")
    open fun greetingBean(): GreetingBean {
        return GreetingBean()
    }
}