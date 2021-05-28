package xyz.srclab.spring.boot.autoconfigure

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import xyz.srclab.spring.boot.core.BoatSpringBootStartedBean
import xyz.srclab.spring.boot.core.CoreProperties
import xyz.srclab.spring.boot.core.DefaultStartGreeting
import xyz.srclab.spring.boot.core.StartGreeting
import xyz.srclab.spring.boot.lang.LangBeanFactoryPostProcessor

@Configuration
open class BoatAutoConfiguration {

    @ConfigurationProperties(prefix = "boat.core")
    @ConditionalOnMissingBean
    @Bean("xyz.srclab.spring.boot.core.CoreProperties")
    open fun coreProperties(): CoreProperties {
        return CoreProperties()
    }

    @ConditionalOnMissingBean
    @Bean("xyz.srclab.spring.boot.core.StartGreeting")
    open fun startGreeting(): StartGreeting {
        return DefaultStartGreeting()
    }

    @Bean("xyz.srclab.spring.boot.core.BoatSpringBootStartedBean")
    open fun boatSpringBootStartedBean(): BoatSpringBootStartedBean {
        return BoatSpringBootStartedBean()
    }

    @Bean("xyz.srclab.spring.boot.lang.LangBeanFactoryPostProcessor")
    open fun langBeanFactoryPostProcessor(): LangBeanFactoryPostProcessor {
        return LangBeanFactoryPostProcessor()
    }
}