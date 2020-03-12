package other.test.easy

import org.springframework.stereotype.Component
import xyz.srclab.spring.boot.bean.configure.BeanConfigure
import xyz.srclab.spring.boot.bean.configure.BeanDefinitionContext
import xyz.srclab.spring.boot.test.TestMarker

@Component
class TestBeanConfigure : BeanConfigure, TestMarker {

    override fun configureBeanDefinition(context: BeanDefinitionContext) {
        mark("TestBeanConfigure.configureBeanDefinition")
    }
}