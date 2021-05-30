package sample.java.xyz.srclab.spring.boot.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;
import xyz.srclab.spring.boot.autoconfigure.BoatAutoConfiguration;
import xyz.srclab.spring.boot.core.StartGreeting;

@SpringBootTest(classes = {
    BoatAutoConfiguration.class,
    GreetingSample.class
})
public class GreetingSample extends AbstractTestNGSpringContextTests implements StartGreeting {

    private static final Logger log = LoggerFactory.getLogger(GreetingSample.class);

    @Test
    public void testAutoConfigure() {
    }

    @Override
    public void doGreeting() {
        log.info(">>>>>>>>>>>>>>>>>> This is sample greeting!");
    }
}
