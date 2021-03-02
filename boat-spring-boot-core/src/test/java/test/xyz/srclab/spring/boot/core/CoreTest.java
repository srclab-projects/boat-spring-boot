package test.xyz.srclab.spring.boot.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;
import xyz.srclab.annotations.Nullable;
import xyz.srclab.spring.boot.core.CoreBean;

import javax.annotation.Resource;

@SpringBootTest
//@ContextConfiguration(classes = {TestStarter.class})
public class CoreTest extends AbstractTestNGSpringContextTests {

    private static final Logger logger = LoggerFactory.getLogger(CoreTest.class);

    @Resource
    private CoreBean coreBean;

    @Test
    public void testAutoConfigure() {
        @Nullable String coreProperty = coreBean.getCoreProperty();
        logger.info("Core property: {}", coreProperty);
        Assert.assertEquals(coreProperty, "1234");
    }
}
