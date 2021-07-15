package sample.kotlin.xyz.srclab.spring.boot.exception

import org.slf4j.LoggerFactory
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests
import org.testng.Assert
import org.testng.annotations.Test
import xyz.srclab.common.exception.ExceptionStatus
import xyz.srclab.spring.boot.autoconfigure.BoatAutoConfiguration
import xyz.srclab.spring.boot.exception.EnableExceptionHandlingService
import xyz.srclab.spring.boot.exception.ExceptionHandlingComponent
import xyz.srclab.spring.boot.exception.ExceptionHandlingMethod
import xyz.srclab.spring.boot.exception.ExceptionHandlingService
import javax.annotation.Resource

@SpringBootTest(
    classes = [
        BoatAutoConfiguration::class, ExceptionHandler::class]
)
@EnableExceptionHandlingService
class ExceptionServiceSample : AbstractTestNGSpringContextTests() {

    @Resource
    private lateinit var exceptionHandlingService: ExceptionHandlingService

    @Test
    fun testExceptionStateService() {
        val runtime = exceptionHandlingService.handle<ExceptionStatus>(RuntimeException())
        log.info("runtime: {}", runtime)
        Assert.assertEquals(runtime.code, "102")
        val throwable = exceptionHandlingService.handle<ExceptionStatus>(Exception())
        log.info("throwable: {}", throwable)
        Assert.assertEquals(throwable.code, "101")
    }

    companion object {
        private val log = LoggerFactory.getLogger(ExceptionServiceSample::class.java)
    }
}

@ExceptionHandlingComponent
class ExceptionHandler {

    @ExceptionHandlingMethod
    fun handle(exception: RuntimeException): ExceptionStatus {
        return ExceptionStatus.of("102")
    }

    @ExceptionHandlingMethod
    fun handle(throwable: Throwable): ExceptionStatus {
        return ExceptionStatus.of("101")
    }
}