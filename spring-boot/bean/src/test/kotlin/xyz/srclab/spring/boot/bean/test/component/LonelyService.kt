package xyz.srclab.spring.boot.bean.test.component

import org.springframework.beans.factory.annotation.Autowired
import javax.annotation.Resource

open class LonelyService {

    @Resource
    private lateinit var testService1: TestService1

    @Resource
    private lateinit var testService2: TestService2

    @Resource
    private lateinit var testService3: TestService3

    @Autowired
    private lateinit var testService4: TestService4

    @Autowired
    private lateinit var testService5: TestService5

    fun printReturn(): String {
        return printReturn(this, testService1)
    }
}