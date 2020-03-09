package xyz.srclab.spring.boot.common.test.exception

import org.testng.Assert
import org.testng.annotations.Test
import xyz.srclab.spring.boot.common.exception.BusinessException
import xyz.srclab.spring.boot.common.exception.CommonExceptionStatus
import xyz.srclab.spring.boot.common.exception.ExceptionWrapper
import java.io.IOException

object ExceptionTest {

    @Test
    fun testBusinessException() {
        val businessException1 = BusinessException(CommonExceptionStatus.INTERNAL)
        println(businessException1)
        val cause = NullPointerException()
        val businessException2 = BusinessException(CommonExceptionStatus.INTERNAL, cause)
        println(businessException2.message)
        Assert.assertEquals(businessException1.message, CommonExceptionStatus.INTERNAL.toString())
        println(businessException1.withMoreDescription("haha"))
        Assert.assertEquals(
            businessException1.withMoreDescription("haha").toString(),
            CommonExceptionStatus.INTERNAL.withMoreDescription("haha").toString()
        )
    }

    @Test
    fun testExceptionWrapper() {
        val wrapper = ExceptionWrapper(IOException())
        wrapper.printStackTrace()
    }
}