package test.xyz.srclab.spring.boot.web.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;
import xyz.srclab.spring.boot.autoconfigure.BoatAutoConfiguration;

import javax.annotation.Resource;

@SpringBootTest(
    classes = {
        BoatAutoConfiguration.class,
        SecurityMvcConfig.class,
    },
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@EnableAutoConfiguration
public class SecurityTest extends AbstractTestNGSpringContextTests {

    private static final Logger logger = LoggerFactory.getLogger(SecurityTest.class);

    @LocalServerPort
    private int port;

    @Resource
    private TestRestTemplate restTemplate;

    @Test
    public void testServlet() {
        String result = restTemplate.postForObject(
            "http://localhost:" + port + "/test/servlet",
            "ppp1",
            String.class
        );
        logger.info("/test/servlet: " + result);
        Assert.assertEquals(result, "ppp1");

        result = restTemplate.postForObject(
            "http://localhost:" + port + "/test/index",
            "ppp2",
            String.class
        );
        logger.info("/test/index: " + result);
        Assert.assertEquals(result, "encode: ppp2");
    }
}
