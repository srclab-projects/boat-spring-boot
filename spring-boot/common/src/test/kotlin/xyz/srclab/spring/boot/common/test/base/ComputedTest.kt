package xyz.srclab.spring.boot.common.test.base

import org.testng.Assert
import org.testng.annotations.Test
import xyz.srclab.spring.boot.common.base.Computed

object ComputedTest {

    @Test
    fun testNoTimeout() {
        val count = arrayOf(0)
        val computed = Computed {
            count[0]++
            count[0]
        }
        println(computed.get())
        println(computed.get())
        println(computed.get())
        Assert.assertEquals(computed.get(), 1)
        Assert.assertEquals(computed.refreshAndGet(), 2)
    }

    @Test
    fun testTimeout() {
        val count = arrayOf(0)
        val computed = Computed(1) {
            count[0]++
            count[0]
        }
        println(computed.get())
        Thread.sleep(1000)
        println(computed.get())
        Thread.sleep(1000)
        println(computed.get())
        println(computed.get())
        println(computed.get())
        Thread.sleep(1000)
        Assert.assertEquals(computed.get(), 4)
        Assert.assertEquals(computed.refreshAndGet(), 5)
    }
}