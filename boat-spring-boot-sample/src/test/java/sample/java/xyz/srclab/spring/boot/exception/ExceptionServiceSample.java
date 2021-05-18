package sample.java.xyz.srclab.spring.boot.exception;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;
import xyz.srclab.common.exception.ExceptionStatus;
import xyz.srclab.spring.boot.exception.EnableExceptionHandlingService;
import xyz.srclab.spring.boot.exception.ExceptionHandlingService;

import javax.annotation.Resource;

@SpringBootTest(classes = Starter.class)
@EnableExceptionHandlingService
public class ExceptionServiceSample extends AbstractTestNGSpringContextTests {

    @Resource
    private ExceptionHandlingService exceptionHandlingService;

    @Test
    public void testExceptionStateService() {
        ExceptionStatus runtime = exceptionHandlingService.toState(new RuntimeException());
        Assert.assertEquals(runtime.code(), "102");
        ExceptionStatus throwable = exceptionHandlingService.toState(new Exception());
        Assert.assertEquals(throwable.code(), "101");
    }
}
