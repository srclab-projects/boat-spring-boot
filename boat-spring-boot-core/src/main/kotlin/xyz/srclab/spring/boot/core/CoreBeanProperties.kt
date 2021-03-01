package xyz.srclab.spring.boot.core

import org.springframework.boot.context.properties.ConfigurationProperties

/**
 * @author sunqian
 */
@ConfigurationProperties(prefix = "boat.core")
open class CoreBeanProperties {

    var coreProperty: String? = null
}