package xyz.srclab.spring.boot.web.exception

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.context.annotation.Bean

//@Configuration
open class WebExceptionServiceConfiguration {

    @ConditionalOnMissingBean
    @Bean("xyz.srclab.spring.boot.web.exception.WebExceptionService")
    open fun webExceptionService(): WebExceptionService {
        return WebExceptionService()
    }

    @ConditionalOnMissingBean
    @Bean("xyz.srclab.spring.boot.web.exception.WebExceptionAdvice")
    open fun webExceptionAdvice(): WebExceptionAdvice {
        return WebExceptionAdvice()
    }
}