package test.xyz.srclab.spring.boot.schedule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.testng.Assert;
import xyz.srclab.common.lang.Current;

public class ScheduleService {

    private static final Logger logger = LoggerFactory.getLogger(ScheduleService.class);

    @Scheduled(cron = "* * * * * *")
    public void testSchedule() {
        logger.info("Thread: {}", Current.thread().getName());
        Assert.assertTrue(Current.thread().getName().startsWith("6666"));
    }
}
