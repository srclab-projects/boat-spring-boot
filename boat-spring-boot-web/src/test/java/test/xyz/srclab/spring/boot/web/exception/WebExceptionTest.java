package test.xyz.srclab.spring.boot.web.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;
import xyz.srclab.common.exception.ExceptionStatus;
import xyz.srclab.common.serialize.json.JsonSerials;
import xyz.srclab.spring.boot.web.exception.EnableWebExceptionService;

import javax.annotation.Resource;

@SpringBootTest(
    classes = Starter.class,
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@EnableWebExceptionService
public class WebExceptionTest extends AbstractTestNGSpringContextTests {

    private static final Logger logger = LoggerFactory.getLogger(WebExceptionTest.class);

    @LocalServerPort
    private int port;

    @Resource
    private TestRestTemplate restTemplate;

    @Test
    public void testException() {
        String result = restTemplate.getForObject(
            "http://localhost:" + port + "/test/exception?body=testException",
            String.class
        );
        logger.info("/test/exception?body=testException: {}", result);
        Assert.assertEquals(result, JsonSerials.toJsonString(new TestController.ResponseMessage()));

        result = restTemplate.getForObject(
            "http://localhost:" + port + "/test/exception?body=testException0",
            String.class
        );
        logger.info("/test/exception?body=testException0: {}", result);
        Assert.assertEquals(result, JsonSerials.toJsonString(ExceptionStatus.of("102")));

        ResponseEntity<String> entity = restTemplate.getForEntity(
            "http://localhost:" + port + "/test/webException?body=testWebException0",
            String.class
        );
        logger.info("/test/webException?body=testWebException0: {}", result);
        Assert.assertEquals(entity.getStatusCode(), HttpStatus.INTERNAL_SERVER_ERROR);
        Assert.assertEquals(entity.getBody(), JsonSerials.toJsonString(ExceptionStatus.of("103")));
    }
}
