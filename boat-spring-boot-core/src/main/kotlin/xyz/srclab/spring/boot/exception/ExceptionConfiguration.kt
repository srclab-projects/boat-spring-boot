package xyz.srclab.spring.boot.exception

import org.springframework.context.annotation.Bean

open class ExceptionConfiguration {

    @Bean("xyz.srclab.spring.boot.exception.ExceptionHandlingService")
    open fun exceptionHandlingService(): ExceptionHandlingService {
        return ExceptionHandlingService()
    }
}