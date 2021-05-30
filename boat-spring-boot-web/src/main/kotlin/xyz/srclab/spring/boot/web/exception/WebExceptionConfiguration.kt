package xyz.srclab.spring.boot.web.exception

import org.springframework.context.annotation.Bean
import xyz.srclab.spring.boot.exception.EnableExceptionHandlingService

@EnableExceptionHandlingService
open class WebExceptionConfiguration {

    @Bean("xyz.srclab.spring.boot.web.exception.WebExceptionService")
    open fun webExceptionService(): WebExceptionService {
        return WebExceptionService()
    }

    @Bean("xyz.srclab.spring.boot.web.exception.WebExceptionAdvice")
    open fun webExceptionAdvice(): WebExceptionAdvice {
        return WebExceptionAdvice()
    }
}