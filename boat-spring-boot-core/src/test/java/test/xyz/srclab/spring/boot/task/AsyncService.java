package test.xyz.srclab.spring.boot.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.scheduling.annotation.Async;
import org.testng.Assert;
import xyz.srclab.common.lang.Current;

public class AsyncService {

    private static final Logger logger = LoggerFactory.getLogger(AsyncService.class);

    @Async
    public void testAsync() {
        logger.info(
            "Thread: {}",
            Current.thread().getName()
        );
        Assert.assertTrue(Current.thread().getName().startsWith("6666"));
        Assert.assertEquals(MDC.get("123"), "123");
    }
}
