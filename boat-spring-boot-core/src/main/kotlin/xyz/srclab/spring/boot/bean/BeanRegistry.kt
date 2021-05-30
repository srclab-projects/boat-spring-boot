package xyz.srclab.spring.boot.bean

import org.springframework.context.ConfigurableApplicationContext
import org.springframework.context.annotation.DependsOn
import xyz.srclab.common.lang.INAPPLICABLE_JVM_NAME
import xyz.srclab.common.lang.loadClass
import javax.annotation.PostConstruct
import javax.annotation.Resource

/**
 * Help register bean dynamically.
 *
 * Override methods:
 * * [registeredSingletons] to register singletons without autowire;
 * * [registeredBeans] to register bean with autowire.
 *
 * Note use [DependsOn] to make sure dynamic beans are registered before autowired.
 *
 * @see BeanProperties
 */
abstract class BeanRegistry {

    @Resource
    private lateinit var configurableApplicationContext: ConfigurableApplicationContext

    @Suppress(INAPPLICABLE_JVM_NAME)
    protected abstract val registeredSingletons: Map<String, Any>
        @JvmName("registeredSingletons") get

    @Suppress(INAPPLICABLE_JVM_NAME)
    protected abstract val registeredBeans: Set<BeanProperties>
        @JvmName("registeredBeans") get

    @PostConstruct
    private fun register() {
        val beanFactory = configurableApplicationContext.beanFactory
        if (registeredSingletons.isNotEmpty()) {
            for (registeredSingleton in registeredSingletons) {
                beanFactory.registerSingleton(registeredSingleton.key, registeredSingleton.value)
            }
        }
        if (registeredBeans.isNotEmpty()) {
            for (registeredBean in registeredBeans) {
                val bean = beanFactory.createBean(registeredBean.className.loadClass<Any>())
                beanFactory.registerSingleton(registeredBean.name, bean)
            }
        }
    }
}