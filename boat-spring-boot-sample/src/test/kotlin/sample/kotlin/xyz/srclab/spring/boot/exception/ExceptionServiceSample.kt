package sample.kotlin.xyz.srclab.spring.boot.exception

import org.slf4j.LoggerFactory
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests
import org.testng.Assert
import org.testng.annotations.Test
import sample.java.xyz.srclab.spring.boot.exception.RuntimeExceptionHandler
import sample.java.xyz.srclab.spring.boot.exception.ThrowableHandler
import xyz.srclab.common.exception.ExceptionStatus
import xyz.srclab.spring.boot.autoconfigure.BoatAutoConfiguration
import xyz.srclab.spring.boot.exception.EnableExceptionHandlingService
import xyz.srclab.spring.boot.exception.ExceptionHandlingComponent
import xyz.srclab.spring.boot.exception.ExceptionHandlingService
import javax.annotation.Resource

@SpringBootTest(
    classes = [
        BoatAutoConfiguration::class, RuntimeExceptionHandler::class, ThrowableHandler::class]
)
@EnableExceptionHandlingService
class ExceptionServiceSample : AbstractTestNGSpringContextTests() {

    @Resource
    private lateinit var exceptionHandlingService: ExceptionHandlingService

    @Test
    fun testExceptionStateService() {
        val runtime = exceptionHandlingService.handle(
            RuntimeException(),
            ExceptionStatus::class.java
        )
        log.info("runtime: {}", runtime)
        Assert.assertEquals(runtime.code, "102")
        val throwable = exceptionHandlingService.handle(
            Exception(),
            ExceptionStatus::class.java
        )
        log.info("throwable: {}", throwable)
        Assert.assertEquals(throwable.code, "101")
    }

    companion object {
        private val log = LoggerFactory.getLogger(ExceptionServiceSample::class.java)
    }
}

open class RuntimeExceptionHandler :
    ExceptionHandlingComponent<RuntimeException, ExceptionStatus> {
    override fun handle(e: RuntimeException): ExceptionStatus {
        return ExceptionStatus.of("102")
    }
}

open class ThrowableHandler :
    ExceptionHandlingComponent<Throwable, ExceptionStatus> {
    override fun handle(e: Throwable): ExceptionStatus {
        return ExceptionStatus.of("101")
    }
}