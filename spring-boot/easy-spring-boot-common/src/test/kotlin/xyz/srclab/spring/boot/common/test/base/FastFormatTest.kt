package xyz.srclab.spring.boot.common.test.base

import org.testng.Assert
import org.testng.annotations.Test
import xyz.srclab.spring.boot.common.base.FastFormat

object FastFormatTest {

    @Test
    fun testFastFormat() {
        val a1 = NullPointerException()
        val a2 = IllegalArgumentException()
        val fastFormat = FastFormat("a1: {}; a2: {}.", a1, a2)
        println(fastFormat.toString())
        Assert.assertEquals(fastFormat.toString(), "a1: ${a1}; a2: ${a2}.")
    }
}