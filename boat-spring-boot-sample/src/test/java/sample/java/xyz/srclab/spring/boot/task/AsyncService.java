package sample.java.xyz.srclab.spring.boot.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.testng.Assert;
import xyz.srclab.common.base.Current;

@Service
public class AsyncService {

    private static final Logger logger = LoggerFactory.getLogger(AsyncService.class);

    @Async
    public void testAsync() {
        logger.info("Thread: {}", Current.thread().getName());
        Assert.assertTrue(Current.thread().getName().startsWith("666"));
    }
}
