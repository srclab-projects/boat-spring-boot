package xyz.srclab.spring.boot.autoconfigure

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import xyz.srclab.spring.boot.core.CoreBean
import xyz.srclab.spring.boot.core.CoreBeanProperties

//@ConditionalOnMissingBean(CoreAutoConfiguration::class)
@EnableConfigurationProperties(CoreBeanProperties::class)
@Configuration
open class CoreAutoConfiguration {

    @ConditionalOnMissingBean
    @Bean("xyz.srclab.spring.boot.core.CoreBean")
    open fun coreBean(): CoreBean {
        return CoreBean()
    }


//    @Bean("xyz.srclab.spring.boot.core.CoreBeanProperties")
//    open fun coreBeanProperties(): CoreBeanProperties {
//        return CoreBeanProperties()
//    }
}