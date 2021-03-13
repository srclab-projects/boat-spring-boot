package test.xyz.srclab.spring.boot.web.exception;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;
import xyz.srclab.common.exception.ExceptionStatus;
import xyz.srclab.spring.boot.exception.ExceptionStateService;
import xyz.srclab.spring.boot.web.exception.EnableWebExceptionStateService;

import javax.annotation.Resource;

@SpringBootTest(classes = Starter.class)
@EnableWebExceptionStateService
public class WebExceptionTest extends AbstractTestNGSpringContextTests {

    @Resource
    private ExceptionStateService exceptionStateService;

    @Test
    public void testExceptionStateService() {
        ExceptionStatus runtime = exceptionStateService.toState(new RuntimeException());
        Assert.assertEquals(runtime.code(), "102");
        ExceptionStatus throwable = exceptionStateService.toState(new Exception());
        Assert.assertEquals(throwable.code(), "101");
    }
}
