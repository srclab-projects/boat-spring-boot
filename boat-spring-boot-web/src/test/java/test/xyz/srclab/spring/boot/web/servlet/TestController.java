package test.xyz.srclab.spring.boot.web.servlet;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("test")
@RestController
public class TestController {

    @RequestMapping("servlet")
    public String testException(String p1) {
        return p1;
    }
}
