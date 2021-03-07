package sample.kotlin.xyz.srclab.spring.boot.core

import org.springframework.beans.PropertyValues
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory
import org.springframework.beans.factory.support.BeanDefinitionRegistry
import org.springframework.stereotype.Component
import xyz.srclab.spring.boot.bean.BeanLifecyclePostProcessor
import java.util.*

@Component
open class MyBeanLifecyclePostProcessor : BeanLifecyclePostProcessor {

    private val sequence: MutableList<String> = LinkedList()
    private var isPostProcessBeanDefinitionRegistry = false
    private var isPostProcessBeanFactory = false
    private var isPostProcessBeforeInstantiation = false
    private var isPostProcessAfterInstantiation = false
    private var isPostProcessBeforeInitialization = false
    private var isPostProcessAfterInitialization = false
    private var isPostProcessProperties = false

    override fun postProcessBeanDefinitionRegistry(registry: BeanDefinitionRegistry) {
        if (!isPostProcessBeanDefinitionRegistry) {
            sequence.add("postProcessBeanDefinitionRegistry")
            isPostProcessBeanDefinitionRegistry = true
        }
    }

    override fun postProcessBeanFactory(beanFactory: ConfigurableListableBeanFactory) {
        if (!isPostProcessBeanFactory) {
            sequence.add("postProcessBeanFactory")
            isPostProcessBeanFactory = true
        }
    }

    override fun postProcessBeforeInstantiation(beanClass: Class<*>, beanName: String): Any? {
        if (!isPostProcessBeforeInstantiation) {
            sequence.add("postProcessBeforeInstantiation")
            isPostProcessBeforeInstantiation = true
        }
        return null
    }

    override fun postProcessAfterInstantiation(bean: Any, beanName: String): Boolean {
        if (!isPostProcessAfterInstantiation) {
            sequence.add("postProcessAfterInstantiation")
            isPostProcessAfterInstantiation = true
        }
        return true
    }

    override fun postProcessBeforeInitialization(bean: Any, beanName: String): Any? {
        if (!isPostProcessBeforeInitialization) {
            sequence.add("postProcessBeforeInitialization")
            isPostProcessBeforeInitialization = true
        }
        return bean
    }

    override fun postProcessAfterInitialization(bean: Any, beanName: String): Any? {
        if (!isPostProcessAfterInitialization) {
            sequence.add("postProcessAfterInitialization")
            isPostProcessAfterInitialization = true
        }
        return null
    }

    override fun postProcessProperties(pvs: PropertyValues, bean: Any, beanName: String): PropertyValues? {
        if (!isPostProcessProperties) {
            sequence.add("postProcessProperties")
            isPostProcessProperties = true
        }
        return null
    }

    fun getSequence(): List<String> {
        return sequence
    }
}