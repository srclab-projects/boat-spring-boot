package test.xyz.srclab.spring.boot.web.message;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;
import xyz.srclab.common.serialize.json.JsonSerials;
import xyz.srclab.spring.boot.web.message.EnableHttpReqMessageResolving;

import javax.annotation.Resource;

@SpringBootTest(
    classes = Starter.class,
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@EnableHttpReqMessageResolving
public class WebMessageTest extends AbstractTestNGSpringContextTests {

    private static final Logger logger = LoggerFactory.getLogger(WebMessageTest.class);

    @LocalServerPort
    private int port;

    @Resource
    private TestRestTemplate restTemplate;

    @Test
    public void testMessage() {
        String result = restTemplate.getForObject(
            "http://localhost:" + port + "/test/message?req1=req1&req2=req2",
            String.class
        );
        logger.info("/test/exception?req1=req1&req2=req2: " + result);
        RespBody respBody = new RespBody();
        respBody.setResp1("req1");
        respBody.setResp2("req2");
        Assert.assertEquals(result, JsonSerials.toJsonString(respBody));
    }
}
