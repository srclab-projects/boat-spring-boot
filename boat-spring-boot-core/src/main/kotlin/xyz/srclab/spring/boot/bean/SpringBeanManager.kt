package xyz.srclab.spring.boot.bean

import org.springframework.beans.factory.config.BeanFactoryPostProcessor
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory

open class SpringBeanManager : BeanFactoryPostProcessor {

    override fun postProcessBeanFactory(beanFactory: ConfigurableListableBeanFactory) {
        for (mutableEntry in beanFactory.getBeansOfType(SpringBeanRegistration::class.java)) {
            val registration = mutableEntry.value
            for (registryBean in registration.registryBeans()) {
                beanFactory.registerSingleton(registryBean.key, registryBean.value)
            }
            for (registryBeanGenerator in registration.registryBeanGenerators()) {
                beanFactory.registerSingleton(registryBeanGenerator.name, registryBeanGenerator.generate())
            }
        }
    }
}