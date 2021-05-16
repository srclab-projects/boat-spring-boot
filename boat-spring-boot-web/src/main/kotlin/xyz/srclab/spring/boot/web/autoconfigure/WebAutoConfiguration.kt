package xyz.srclab.spring.boot.web.autoconfigure

import org.springframework.context.annotation.Configuration
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import xyz.srclab.spring.boot.web.message.ReqMessageHandlerMethodArgumentResolver

@Configuration
open class WebAutoConfiguration : WebMvcConfigurer {

    override fun addArgumentResolvers(resolvers: MutableList<HandlerMethodArgumentResolver>) {
        resolvers.add(ReqMessageHandlerMethodArgumentResolver())
    }
}