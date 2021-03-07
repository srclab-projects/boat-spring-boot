package sample.java.xyz.srclab.spring.boot.core;

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
//@ContextConfiguration(classes = {TestStarter.class})
@DependsOn("myBeanRegistry")
public class BeanSample extends AbstractTestNGSpringContextTests {

    private static final Logger logger = LoggerFactory.getLogger(BeanSample.class);

    @Resource
    private MyBeanLifecyclePostProcessor myBeanLifecyclePostProcessor;

    @Resource
    private String bean1;

    @Resource
    private String bean2;

    @Resource
    private MyBean myBean;

    @Test
    public void testBeanPostProcessor() {
        logger.info("Bean processing sequence: {}", myBeanLifecyclePostProcessor.getSequence());
        Assert.assertEquals(
                myBeanLifecyclePostProcessor.getSequence(),
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
        logger.info("myBean: {}", myBean.getBeanString());
        Assert.assertEquals(myBean.getBeanString(), bean1 + bean2);
    }
}
