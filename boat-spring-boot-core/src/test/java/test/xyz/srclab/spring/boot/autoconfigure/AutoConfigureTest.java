package test.xyz.srclab.spring.boot.autoconfigure;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;
import xyz.srclab.spring.boot.autoconfigure.CoreAutoConfiguration;

@ContextConfiguration(classes = {CoreAutoConfiguration.class})
public class AutoConfigureTest extends AbstractTestNGSpringContextTests {

    @Test
    public void testAutoConfigure() {

    }
}
