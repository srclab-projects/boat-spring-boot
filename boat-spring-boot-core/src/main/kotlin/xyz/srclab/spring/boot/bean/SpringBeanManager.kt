package xyz.srclab.spring.boot.bean

import org.springframework.beans.factory.config.ConfigurableListableBeanFactory

open class SpringBeanManager : SpringBeanPostProcessor {

    override fun postProcessBeanFactory(beanFactory: ConfigurableListableBeanFactory) {
        super.postProcessBeanFactory(beanFactory)
        for (mutableEntry in beanFactory.getBeansOfType(SpringBeanRegistration::class.java)) {
            val registration = mutableEntry.value
            for (registryBean in registration.registerSingletons()) {
                beanFactory.registerSingleton(registryBean.key, registryBean.value)
            }
            for (registryBeanGenerator in registration.registerSingletonGenerators()) {
                beanFactory.registerSingleton(registryBeanGenerator.name, registryBeanGenerator.generate())
            }
        }
    }
}