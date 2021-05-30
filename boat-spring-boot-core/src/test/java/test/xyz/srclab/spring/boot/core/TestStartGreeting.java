package test.xyz.srclab.spring.boot.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.testng.Assert;
import xyz.srclab.spring.boot.core.StartGreeting;

/**
 * @author sunqian
 */
public class TestStartGreeting implements StartGreeting, ApplicationRunner {

    private static final Logger logger = LoggerFactory.getLogger(TestStartGreeting.class);

    public String greeting = "old greeting";

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Assert.assertEquals(greeting, "new greeting");
    }

    @Override
    public void doGreeting() {
        logger.info("Old greeting: {}, new greeting: {}", greeting, "new greeting");
        greeting = "new greeting";
    }
}
