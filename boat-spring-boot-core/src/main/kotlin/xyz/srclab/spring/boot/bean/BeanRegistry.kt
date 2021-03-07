package xyz.srclab.spring.boot.bean

import org.springframework.context.ConfigurableApplicationContext
import xyz.srclab.common.base.loadClass
import javax.annotation.PostConstruct
import javax.annotation.Resource

/**
 * Help register bean dynamically.
 *
 * Override methods:
 * * [registerSingletons] to register singletons without autowire;
 * * [registerBeans] to register bean with autowire.
 *
 * Note use @DependOn to make sure dynamic beans are registered before autowired.
 *
 * @see BeanProperties
 */
abstract class BeanRegistry {

    @Resource
    private lateinit var configurableApplicationContext: ConfigurableApplicationContext

    protected abstract fun registerSingletons(): Map<String, Any>

    protected abstract fun registerBeans(): Set<BeanProperties>

    @PostConstruct
    private fun register() {
        val beanFactory = configurableApplicationContext.beanFactory
        val registerSingletons = registerSingletons()
        if (registerSingletons.isNotEmpty()) {
            for (registerSingleton in registerSingletons) {
                beanFactory.registerSingleton(registerSingleton.key, registerSingleton.value)
            }
        }
        val registerBeans = registerBeans()
        if (registerBeans.isNotEmpty()) {
            for (registerBean in registerBeans) {
                val bean = beanFactory.createBean(registerBean.className.loadClass<Any>())
                beanFactory.registerSingleton(registerBean.name, bean)
            }
        }
    }
}