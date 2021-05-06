package sample.kotlin.xyz.srclab.spring.boot.bean

import org.springframework.stereotype.Component
import xyz.srclab.spring.boot.bean.BeanProperties
import xyz.srclab.spring.boot.bean.BeanRegistry

@Component
open class MyBeanRegistry : BeanRegistry() {

    override val registeredSingletons: Map<String, Any>
        get() {
            val result: MutableMap<String, Any> = HashMap()
            result["bean1"] = "bean1"
            result["bean2"] = "bean2"
            return result
        }

    override val registeredBeans: Set<BeanProperties>
        get() {
            val result: MutableSet<BeanProperties> = HashSet()
            val beanProperties = BeanProperties()
            beanProperties.name = "myBean"
            beanProperties.className = MyBean::class.java.name
            result.add(beanProperties)
            return result
        }
}