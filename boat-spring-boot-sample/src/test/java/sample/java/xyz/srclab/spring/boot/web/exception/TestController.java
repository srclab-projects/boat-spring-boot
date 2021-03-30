package sample.java.xyz.srclab.spring.boot.web.exception;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("test")
@RestController
public class TestController {

    @RequestMapping("exception")
    public String testException(String body) {
        if ("testException".equals(body)) {
            return body;
        }
        throw new IllegalArgumentException("Must be testException!");
    }
}
