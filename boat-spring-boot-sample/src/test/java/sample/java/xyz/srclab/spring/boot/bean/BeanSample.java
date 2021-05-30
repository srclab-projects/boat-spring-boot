package sample.java.xyz.srclab.spring.boot.bean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;
import xyz.srclab.spring.boot.autoconfigure.BoatAutoConfiguration;

import javax.annotation.Resource;
import java.util.Arrays;

@SpringBootTest(classes = {
    BoatAutoConfiguration.class,
    MyBean.class,
    MyBeanLifecyclePostProcessor.class,
    MyBeanRegistry.class,
})
public class BeanSample extends AbstractTestNGSpringContextTests {

    private static final Logger log = LoggerFactory.getLogger(BeanSample.class);

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
        log.info("Bean processing sequence: {}", myBeanLifecyclePostProcessor.getSequence());
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
        log.info("bean1: {}", bean1);
        Assert.assertEquals(bean1, "bean1");
        log.info("bean2: {}", bean2);
        Assert.assertEquals(bean2, "bean2");
        log.info("myBean: {}", myBean.getBeanString());
        Assert.assertEquals(myBean.getBeanString(), bean1 + bean2);
    }
}
