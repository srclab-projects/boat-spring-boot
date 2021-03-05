package xyz.srclab.spring.boot.bean

import xyz.srclab.common.base.INAPPLICABLE_JVM_NAME

/**
 * To registry spring bean with singleton.
 *
 * @see SpringSingletonGenerator
 */
interface SpringBeanRegistration {

    @JvmDefault
    fun registerSingletons(): Map<String, Any> {
        return emptyMap()
    }

    @JvmDefault
    fun registerSingletonGenerators(): Set<SpringSingletonGenerator> {
        return emptySet()
    }
}

/**
 * To generate singleton bean by [SpringBeanRegistration]
 */
interface SpringSingletonGenerator {

    @Suppress(INAPPLICABLE_JVM_NAME)
    val name: String
        @JvmName("name") get

    fun generate(): Any
}