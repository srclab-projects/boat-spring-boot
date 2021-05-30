package sample.java.xyz.srclab.spring.boot.task;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;
import xyz.srclab.common.lang.Current;
import xyz.srclab.spring.boot.autoconfigure.BoatAutoConfiguration;

import javax.annotation.Resource;

@SpringBootTest(classes = {
    BoatAutoConfiguration.class,
    TaskExecutorConfiguration.class,
    AsyncService.class,
})
public class TaskSample extends AbstractTestNGSpringContextTests {

    @Resource
    private AsyncService asyncService;

    @Test
    public void testTask() {
        asyncService.testAsync();
        Current.sleep(1000);
    }
}
