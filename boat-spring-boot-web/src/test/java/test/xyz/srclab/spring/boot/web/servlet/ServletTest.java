package test.xyz.srclab.spring.boot.web.servlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;

import javax.annotation.Resource;

@SpringBootTest(
        classes = Starter.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
public class ServletTest extends AbstractTestNGSpringContextTests {

    private static final Logger logger = LoggerFactory.getLogger(ServletTest.class);

    @LocalServerPort
    private int port;

    @Resource
    private TestRestTemplate restTemplate;

    @Test
    public void testException() {
        String result = restTemplate.postForObject(
                "http://localhost:" + port + "/test/servlet",
                "ppp1",
                String.class
        );
        logger.info("/test/exception?body=testException: " + result);
        Assert.assertEquals(result, "ppp1");
    }
}
