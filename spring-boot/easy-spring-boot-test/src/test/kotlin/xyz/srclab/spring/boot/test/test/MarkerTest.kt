package xyz.srclab.spring.boot.test.test

import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests
import org.testng.Assert
import org.testng.annotations.Test
import javax.annotation.Resource

@SpringBootTest(classes = [Config::class])
open class MarkerTest : AbstractTestNGSpringContextTests() {

    @Resource
    private lateinit var someBean: SomeBean

    @Test
    fun testMark() {
        someBean.printMarkInfo()
        Assert.assertEquals(someBean.getActualMark("666"), someBean.generateMark("666"))
    }
}