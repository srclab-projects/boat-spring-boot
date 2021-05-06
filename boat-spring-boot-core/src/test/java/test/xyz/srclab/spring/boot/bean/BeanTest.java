package test.xyz.srclab.spring.boot.bean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.DependsOn;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;

import javax.annotation.Resource;
import java.util.Arrays;

@SpringBootTest(classes = Starter.class)
@DependsOn("testRegistry")
//@ContextConfiguration(classes = {TestStarter.class})
public class BeanTest extends AbstractTestNGSpringContextTests {

    private static final Logger logger = LoggerFactory.getLogger(BeanTest.class);

    @Resource
    private TestBeanLifecyclePostProcessor testBeanPostProcessor;

    @Resource
    private TestProperties testProperties;

    @Resource
    private TestBean testBean;

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
        logger.info("testBean.getBeanString(): {}", testBean.getBeanString());
        Assert.assertEquals(testBean.getBeanString(), testProperties.getBean1() + testProperties.getBean2());
    }
}
