package sample.java.xyz.srclab.spring.boot.web.exception;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RequestMapping("test")
@RestController
public class TestController {

    @RequestMapping("illegalState")
    public Map<Object, Object> testIllegalState() {
        throw new IllegalStateException();
    }

    @RequestMapping("runtimeException")
    public Map<Object, Object> testRuntimeException() {
        throw new RuntimeException();
    }

    @RequestMapping("exception")
    public Map<Object, Object> testException() throws Exception {
        throw new Exception();
    }
}
