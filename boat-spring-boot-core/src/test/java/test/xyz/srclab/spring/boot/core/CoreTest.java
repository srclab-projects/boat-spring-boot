package test.xyz.srclab.spring.boot.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;
import xyz.srclab.spring.boot.core.CoreProperties;

import javax.annotation.Resource;

@SpringBootTest
//@ContextConfiguration(classes = {TestStarter.class})
public class CoreTest extends AbstractTestNGSpringContextTests {

    private static final Logger logger = LoggerFactory.getLogger(CoreTest.class);

    @Resource
    private CoreProperties coreProperties;

    @Test
    public void testAutoConfigure() {
        logger.info("Core spring version: {}", coreProperties.getSpringVersion());
        Assert.assertEquals(coreProperties.getSpringVersion(), "666");
    }
}
