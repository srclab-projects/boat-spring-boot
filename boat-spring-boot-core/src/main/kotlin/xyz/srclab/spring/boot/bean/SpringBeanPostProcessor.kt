package xyz.srclab.spring.boot.bean

import org.springframework.beans.PropertyValues
import org.springframework.beans.factory.config.BeanFactoryPostProcessor
import org.springframework.beans.factory.config.BeanPostProcessor
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor
import org.springframework.beans.factory.support.BeanDefinitionRegistry
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor

/**
 * Global Spring bean post processor:
 * ```
 * Spring starts
 *   -> load bean definition
 *   -> BeanDefinitionRegistryPostProcessor.postProcessBeanDefinitionRegistry
 *   -> BeanFactoryPostProcessor.postProcessBeanFactory
 *   -> InstantiationAwareBeanPostProcessor.postProcessBeforeInstantiation
 *   -> Create bean
 *   -> InstantiationAwareBeanPostProcessor.postProcessAfterInstantiation
 *   -> Autowire (InstantiationAwareBeanPostProcessor.postProcessProperties)
 *   -> BeanPostProcessor.postProcessBeforeInitialization
 *   -> Bean init
 *   -> BeanPostProcessor.postProcessAfterInitialization
 * ```
 */
interface SpringBeanPostProcessor :
    BeanDefinitionRegistryPostProcessor,
    BeanFactoryPostProcessor,
    InstantiationAwareBeanPostProcessor,
    BeanPostProcessor {

    @JvmDefault
    override fun postProcessBeanDefinitionRegistry(registry: BeanDefinitionRegistry) {
    }

    @JvmDefault
    override fun postProcessBeanFactory(beanFactory: ConfigurableListableBeanFactory) {
    }

    @JvmDefault
    override fun postProcessBeforeInstantiation(beanClass: Class<*>, beanName: String): Any? {
        return super.postProcessBeforeInstantiation(beanClass, beanName)
    }

    @JvmDefault
    override fun postProcessAfterInstantiation(bean: Any, beanName: String): Boolean {
        return super.postProcessAfterInstantiation(bean, beanName)
    }

    @JvmDefault
    override fun postProcessProperties(pvs: PropertyValues, bean: Any, beanName: String): PropertyValues? {
        return super.postProcessProperties(pvs, bean, beanName)
    }

    @JvmDefault
    override fun postProcessBeforeInitialization(bean: Any, beanName: String): Any? {
        return super<InstantiationAwareBeanPostProcessor>.postProcessBeforeInitialization(bean, beanName)
    }

    @JvmDefault
    override fun postProcessAfterInitialization(bean: Any, beanName: String): Any? {
        return super<InstantiationAwareBeanPostProcessor>.postProcessAfterInitialization(bean, beanName)
    }
}