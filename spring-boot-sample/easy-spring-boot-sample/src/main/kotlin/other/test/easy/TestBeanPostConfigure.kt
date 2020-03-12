package other.test.easy

import org.springframework.stereotype.Component
import xyz.srclab.spring.boot.bean.configure.BeanPostConfigure
import xyz.srclab.spring.boot.bean.configure.BeanPostContext
import xyz.srclab.spring.boot.test.TestMarker

@Component
class TestBeanPostConfigure : BeanPostConfigure, TestMarker {

    override fun configureBeforeInitialization(context: BeanPostContext): Any {
        mark("TestBeanPostConfigure.configureBeforeInitialization")
        return context.bean
    }

    override fun configureAfterInitialization(context: BeanPostContext): Any {
        mark("TestBeanPostConfigure.configureAfterInitialization")
        return context.bean
    }
}