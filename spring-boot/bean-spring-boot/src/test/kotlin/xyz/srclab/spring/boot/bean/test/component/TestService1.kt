package xyz.srclab.spring.boot.bean.test.component

import org.springframework.stereotype.Component
import javax.annotation.Resource

@TestAnnotation1
@Component
open class TestService1 {

    @Resource
    private lateinit var testService2: TestService2

    fun service(): String = "provide by $javaClass"
}