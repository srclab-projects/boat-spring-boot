package test.xyz.srclab.spring.boot.bean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;

import javax.annotation.Resource;
import java.util.Arrays;

@SpringBootTest
//@ContextConfiguration(classes = {TestStarter.class})
public class BeanTest extends AbstractTestNGSpringContextTests {

    private static final Logger logger = LoggerFactory.getLogger(BeanTest.class);

    @Resource
    private TestBeanPostProcessor testBeanPostProcessor;

    @Resource
    private String bean1;

    @Resource
    private String bean2;

    @Test
    public void testBeanPostProcessor() {
        logger.info("Bean processing sequence: {}", testBeanPostProcessor.getSequence());
        Assert.assertEquals(
                testBeanPostProcessor.getSequence(),
                Arrays.asList(
                        "postProcessBeanDefinitionRegistry",
                        "postProcessBeanFactory",
                        "postProcessBeforeInstantiation",
                        "postProcessAfterInstantiation",
                        "postProcessProperties",
                        "postProcessBeforeInitialization",
                        "postProcessAfterInitialization"
                )
        );
    }

    @Test
    public void testBeanManager() {
        logger.info("bean1: {}", bean1);
        Assert.assertEquals(bean1, "bean1");
        logger.info("bean2: {}", bean2);
        Assert.assertEquals(bean2, "bean2");
    }
}
