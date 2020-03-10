package xyz.srclab.spring.boot.bean.test.configure

import org.springframework.beans.factory.config.AutowireCapableBeanFactory
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests
import org.testng.Assert
import org.testng.annotations.Test
import xyz.srclab.spring.boot.bean.test.component.*
import xyz.srclab.spring.boot.bean.test.config.Config
import javax.annotation.Resource

@SpringBootTest(classes = [Config::class])
class ConfigureTest : AbstractTestNGSpringContextTests() {

    @Resource
    private lateinit var dynaService1: DynaService1

    @Resource
    private lateinit var dynaService2: DynaService2

    @Resource
    private lateinit var dynaServiceByFactory1: DynaServiceByFactory1

    @Resource
    private lateinit var dynaServiceByFactory2: DynaServiceByFactory2

    @Resource
    private lateinit var dynaServiceFactoryBean1: DynaServiceByFactory1

    @Resource
    private lateinit var dynaServiceFactoryBean2: DynaServiceByFactory2

    @Resource
    private lateinit var autowireCapableBeanFactory: AutowireCapableBeanFactory

    @Test
    fun testDynaService() {
        Assert.assertEquals(dynaService1.printReturn(), toInjectString(dynaService1, dynaService2))
        Assert.assertEquals(dynaService2.printReturn(), toInjectString(dynaService2, dynaService1))
        Assert.assertEquals(
            dynaServiceByFactory1.printReturn(),
            toInjectString(dynaServiceByFactory1, dynaServiceByFactory2)
        )
        Assert.assertEquals(
            dynaServiceByFactory2.printReturn(),
            toInjectString(dynaServiceByFactory2, dynaServiceByFactory1)
        )
        Assert.assertEquals(
            dynaServiceFactoryBean1.printReturn(),
            toInjectString(dynaServiceFactoryBean1, null)
        )
        Assert.assertEquals(
            dynaServiceFactoryBean2.printReturn(),
            toInjectString(dynaServiceFactoryBean2, null)
        )

        autowireCapableBeanFactory.autowireBean(dynaServiceFactoryBean1)
        autowireCapableBeanFactory.autowireBean(dynaServiceFactoryBean2)
        Assert.assertEquals(
            dynaServiceFactoryBean1.printReturn(),
            toInjectString(dynaServiceFactoryBean1, dynaServiceFactoryBean2)
        )
        Assert.assertEquals(
            dynaServiceFactoryBean2.printReturn(),
            toInjectString(dynaServiceFactoryBean2, dynaServiceFactoryBean1)
        )
    }
}