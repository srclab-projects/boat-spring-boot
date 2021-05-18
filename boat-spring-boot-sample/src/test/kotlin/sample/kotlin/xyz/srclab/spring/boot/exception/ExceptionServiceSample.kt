package sample.kotlin.xyz.srclab.spring.boot.exception

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.stereotype.Component
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests
import org.testng.Assert
import org.testng.annotations.Test
import xyz.srclab.common.exception.ExceptionStatus
import xyz.srclab.spring.boot.exception.EnableExceptionHandlingService
import xyz.srclab.spring.boot.exception.ExceptionHandler
import xyz.srclab.spring.boot.exception.ExceptionHandlingService
import javax.annotation.Resource

@SpringBootTest(classes = [Starter::class])
@EnableExceptionHandlingService
class ExceptionServiceSample : AbstractTestNGSpringContextTests() {

    @Resource
    private lateinit var exceptionHandlingService: ExceptionHandlingService

    @Test
    fun testExceptionStateService() {
        val runtime = exceptionHandlingService.toState<ExceptionStatus>(RuntimeException())
        Assert.assertEquals(runtime.code, "102")
        val throwable = exceptionHandlingService.toState<ExceptionStatus>(Exception())
        Assert.assertEquals(throwable.code, "101")
    }
}

@Component
open class RuntimeExceptionStatusHandler :
    ExceptionHandler<RuntimeException, ExceptionStatus> {
    override val supportedType: Class<RuntimeException> = RuntimeException::class.java
    override fun handle(e: RuntimeException): ExceptionStatus {
        return ExceptionStatus.of("102")
    }
}

@Component
open class ThrowableStatusHandler : ExceptionHandler<Throwable, ExceptionStatus> {
    override val supportedType: Class<Throwable> = Throwable::class.java
    override fun handle(e: Throwable): ExceptionStatus {
        return ExceptionStatus.of("101")
    }
}

@SpringBootApplication
open class Starter