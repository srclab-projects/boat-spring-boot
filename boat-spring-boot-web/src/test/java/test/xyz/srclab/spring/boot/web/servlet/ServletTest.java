package test.xyz.srclab.spring.boot.web.servlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;
import xyz.srclab.common.exception.ExceptionStatus;
import xyz.srclab.spring.boot.web.exception.ExceptionResponseBody;

import javax.annotation.Resource;
import java.util.Objects;

@SpringBootTest(
    classes = {
        TestController.class,
        TestFilter.class,
    },
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@EnableAutoConfiguration
public class ServletTest extends AbstractTestNGSpringContextTests {

    private static final Logger logger = LoggerFactory.getLogger(ServletTest.class);

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
    }

    @Test
    public void testIndex() {
        String result = restTemplate.postForObject(
            "http://localhost:" + port + "/test/index",
            "ppp2",
            String.class
        );
        logger.info("/test/index: " + result);
        Assert.assertEquals(result, "encode: ppp2");
    }

    @Test
    public void testException() {
        ResponseEntity<ExceptionResponseBody> result = restTemplate.getForEntity(
            "http://localhost:" + port + "/test/exception",
            ExceptionResponseBody.class
        );
        logger.info("/test/exception: " + result);
        logger.info("/test/exception: " + restTemplate.getForObject(
            "http://localhost:" + port + "/test/exception",
            String.class
        ));
        Assert.assertEquals(Objects.requireNonNull(result.getBody()).getCode(), ExceptionStatus.INTERNAL.code());
    }
}
