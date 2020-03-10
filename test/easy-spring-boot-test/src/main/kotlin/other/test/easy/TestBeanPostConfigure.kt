package other.test.easy

import org.springframework.stereotype.Component
import xyz.srclab.spring.boot.bean.configure.BeanPostConfigure
import xyz.srclab.spring.boot.bean.configure.BeanPostContext
import xyz.srclab.spring.boot.test.BeanProcessMarker

@Component
class TestBeanPostConfigure : BeanPostConfigure, BeanProcessMarker {

    override fun configureBeforeInitialization(context: BeanPostContext): Any {
        mark("TestBeanPostConfigure.configureBeforeInitialization")
        return context.bean
    }

    override fun configureAfterInitialization(context: BeanPostContext): Any {
        mark("TestBeanPostConfigure.configureAfterInitialization")
        return context.bean
    }
}