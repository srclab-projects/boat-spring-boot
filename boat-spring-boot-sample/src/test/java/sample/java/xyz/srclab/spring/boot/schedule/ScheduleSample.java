package sample.java.xyz.srclab.spring.boot.schedule;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;
import xyz.srclab.common.lang.Current;
import xyz.srclab.spring.boot.autoconfigure.BoatAutoConfiguration;

@SpringBootTest(classes = {
    BoatAutoConfiguration.class,
    TestTaskSchedulerConfiguration.class,
    ScheduleService.class,
})
public class ScheduleSample extends AbstractTestNGSpringContextTests {

    @Test
    public void testSchedule() {
        Current.sleep(2000);
    }
}
