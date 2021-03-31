package test.xyz.srclab.spring.boot.web.servlet;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

@RequestMapping("test")
@RestController
public class TestController {

    @RequestMapping("servlet")
    public String testServlet(String p1) {
        return p1;
    }

    @RequestMapping("index")
    public ModelAndView testIndex(String p1) {
        Map<String, Object> data = new HashMap<>();
        data.put("pm", p1);
        return new ModelAndView("encode", data);
    }

    @RequestMapping("encode")
    public String testEncode(String pm) {
        return "encode: " + pm;
    }
}
