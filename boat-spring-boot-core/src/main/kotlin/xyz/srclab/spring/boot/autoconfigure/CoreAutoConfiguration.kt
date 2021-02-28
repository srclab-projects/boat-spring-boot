package xyz.srclab.spring.boot.autoconfigure

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.context.annotation.Bean
import xyz.srclab.spring.boot.bean.CoreBean

//@ConditionalOnMissingBean(CoreAutoConfiguration::class)
open class CoreAutoConfiguration {

    @Bean("xyz.srclab.spring.boot.bean.CoreBean")
    fun coreBean(): CoreBean {
        return CoreBean()
    }
}