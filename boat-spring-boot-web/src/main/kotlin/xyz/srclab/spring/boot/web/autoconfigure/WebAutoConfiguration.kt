package xyz.srclab.spring.boot.web.autoconfigure

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import xyz.srclab.spring.boot.web.message.MessageProperties

@Configuration
open class WebAutoConfiguration {

    @ConfigurationProperties(prefix = "boat.message")
    @Bean("xyz.srclab.spring.boot.web.message.MessageProperties")
    open fun messageProperties(): MessageProperties {
        return MessageProperties()
    }
}