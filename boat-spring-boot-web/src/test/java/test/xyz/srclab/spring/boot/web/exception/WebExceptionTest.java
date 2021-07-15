package test.xyz.srclab.spring.boot.web.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;
import xyz.srclab.spring.boot.autoconfigure.BoatAutoConfiguration;
import xyz.srclab.spring.boot.web.exception.EnableWebExceptionHandling;
import xyz.srclab.spring.boot.web.exception.ExceptionResponseBody;

import javax.annotation.Resource;
import java.util.Objects;

@SpringBootTest(
    classes = {
        BoatAutoConfiguration.class,
        ExceptionHandler.class,
        TestController.class,
    },
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@EnableWebExceptionHandling
@EnableAutoConfiguration
public class WebExceptionTest extends AbstractTestNGSpringContextTests {

    private static final Logger logger = LoggerFactory.getLogger(WebExceptionTest.class);

    @LocalServerPort
    private int port;

    @Resource
    private TestRestTemplate restTemplate;

    @Test
    public void testIllegalException() {
        ResponseEntity<ExceptionResponseBody> result = restTemplate.getForEntity(
            "http://localhost:" + port + "/test/illegalState",
            ExceptionResponseBody.class
        );
        logger.info("/test/illegalState: {}", result);
        Assert.assertEquals(result.getStatusCode(), HttpStatus.INTERNAL_SERVER_ERROR);
        Assert.assertEquals(Objects.requireNonNull(result.getBody()).getCode(), "101");
    }

    @Test
    public void testRuntimeException() {
        ResponseEntity<ExceptionResponseBody> result = restTemplate.getForEntity(
            "http://localhost:" + port + "/test/runtimeException",
            ExceptionResponseBody.class
        );
        logger.info("/test/runtimeException: {}", result);
        Assert.assertEquals(result.getStatusCode(), HttpStatus.BAD_REQUEST);
        Assert.assertEquals(Objects.requireNonNull(result.getBody()).getCode(), "102");
    }

    @Test
    public void testException() {
        ResponseEntity<String> result3 = restTemplate.getForEntity(
            "http://localhost:" + port + "/test/exception",
            String.class
        );
        logger.info("/test/exception: {}", result3);
        Assert.assertEquals(result3.getStatusCode(), HttpStatus.OK);
        Assert.assertEquals(result3.getBody(), "103");
    }
}
