package xyz.srclab.spring.boot.common.test.proxy

import org.testng.Assert
import org.testng.annotations.Test
import xyz.srclab.spring.boot.common.proxy.ClassProxy
import xyz.srclab.spring.boot.common.test.model.Animal
import xyz.srclab.spring.boot.common.test.model.Bird

object ProxyTest {

    @Test
    fun testClassProxy() {
        val proxy = ClassProxy.newBuilder(Bird::class.java)
            .proxyMethod("fly", arrayOf()) {
                "${it.methodProxy.invoke()}(proxy)"
            }
            .build()
        val proxyBird = proxy.newInstance()
        println(proxyBird.fly())
        println(proxyBird.call())
        println(proxyBird.blow())
        Assert.assertEquals(proxyBird.fly(), "${Bird().fly()}(proxy)")
    }

    @Test
    fun testInterfaceProxy() {
        val proxy = ClassProxy.newBuilder(Animal::class.java)
            .proxyMethod("run", arrayOf()) {
                println("run of ${it.`object`.javaClass}")
                "run()(proxy)"
            }
            .build()
        val proxyAnimal = proxy.newInstance()
        println(proxyAnimal.run())
        Assert.assertEquals(proxyAnimal.run(), "run()(proxy)")
    }
}