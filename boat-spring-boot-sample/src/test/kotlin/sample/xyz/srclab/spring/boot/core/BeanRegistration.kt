package sample.xyz.srclab.spring.boot.core

import org.springframework.stereotype.Component
import xyz.srclab.spring.boot.bean.SpringBeanRegistration
import xyz.srclab.spring.boot.bean.SpringSingletonGenerator
import java.util.*

@Component
class BeanRegistrationKt : SpringBeanRegistration {

    override fun registerSingletons(): Map<String, Any> {
        val map: MutableMap<String, Any> = HashMap()
        map["bean1Kt"] = "bean1Kt"
        return map
    }

    override fun registerSingletonGenerators(): Set<SpringSingletonGenerator> {
        val set: MutableSet<SpringSingletonGenerator> = HashSet()
        set.add(object : SpringSingletonGenerator {

            override val name: String = "bean2Kt"

            override fun generate(): Any {
                return "bean2Kt"
            }
        })
        return set
    }
}