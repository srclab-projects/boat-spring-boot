package xyz.srclab.spring.boot.core

import org.springframework.beans.factory.config.BeanFactoryPostProcessor
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory

/**
 * [BeanFactoryPostProcessor] for core package.
 */
open class CoreFactoryPostProcessor : BeanFactoryPostProcessor {

    override fun postProcessBeanFactory(beanFactory: ConfigurableListableBeanFactory) {
        beanFactory.registerCustomEditor(KeyString::class.java, KeyStringEditor::class.java)
    }
}