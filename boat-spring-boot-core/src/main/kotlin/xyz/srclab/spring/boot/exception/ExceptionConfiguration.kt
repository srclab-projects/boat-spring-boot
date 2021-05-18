package xyz.srclab.spring.boot.exception

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.context.annotation.Bean

open class ExceptionConfiguration {

    @ConditionalOnMissingBean
    @Bean("xyz.srclab.spring.boot.exception.ExceptionHandlingService")
    open fun exceptionHandlingService(): ExceptionHandlingService {
        return ExceptionHandlingService()
    }
}