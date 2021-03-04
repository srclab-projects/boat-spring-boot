package xyz.srclab.spring.boot.autoconfigure

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import xyz.srclab.spring.boot.core.CoreProperties

@Configuration
open class CoreAutoConfiguration {

    @ConfigurationProperties(prefix = "boat.core")
    @ConditionalOnMissingBean
    @Bean
    open fun coreProperties(): CoreProperties {
        return CoreProperties()
    }
}