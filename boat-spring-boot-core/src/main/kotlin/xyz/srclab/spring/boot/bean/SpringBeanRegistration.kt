package xyz.srclab.spring.boot.bean

import xyz.srclab.common.base.INAPPLICABLE_JVM_NAME

/**
 * To registry spring bean with singleton.
 *
 * @see SpringBeanGenerator
 */
interface SpringBeanRegistration {

    @JvmDefault
    fun registryBeans(): Map<String, Any> {
        return emptyMap()
    }

    @JvmDefault
    fun registryBeanGenerators(): Set<SpringBeanGenerator> {
        return emptySet()
    }
}

/**
 * To generate singleton bean by [SpringBeanRegistration]
 */
interface SpringBeanGenerator {

    @Suppress(INAPPLICABLE_JVM_NAME)
    val name: String
        @JvmName("name") get

    fun generate(): Any
}