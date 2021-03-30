package test.xyz.srclab.spring.boot.exception;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;
import xyz.srclab.common.exception.ExceptionStatus;
import xyz.srclab.spring.boot.exception.EnableExceptionService;
import xyz.srclab.spring.boot.exception.ExceptionStatusService;

import javax.annotation.Resource;

@SpringBootTest(classes = Starter.class)
@EnableExceptionService
public class ExceptionServiceTest extends AbstractTestNGSpringContextTests {

    @Resource
    private ExceptionStatusService exceptionStatusService;

    @Test
    public void testExceptionStateService() {
        ExceptionStatus runtime = exceptionStatusService.toState(new RuntimeException());
        Assert.assertEquals(runtime.code(), "102");
        ExceptionStatus throwable = exceptionStatusService.toState(new Exception());
        Assert.assertEquals(throwable.code(), "101");
    }
}
