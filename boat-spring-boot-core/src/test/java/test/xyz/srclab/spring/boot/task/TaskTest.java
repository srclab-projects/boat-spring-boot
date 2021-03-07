package test.xyz.srclab.spring.boot.task;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;
import xyz.srclab.common.base.Current;

import javax.annotation.Resource;

@SpringBootTest(classes = Starter.class)
public class TaskTest extends AbstractTestNGSpringContextTests {

    @Resource
    private AsyncService asyncService;

    @Test
    public void testTask() {
        asyncService.testAsync();
        Current.sleep(1000);
    }
}
