package test.xyz.srclab.spring.boot.web.exception;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.srclab.spring.boot.web.exception.WebStatusException;

@RequestMapping("test")
@RestController
public class TestController {

    @RequestMapping("exception")
    public ResponseMessage testException(String body) {
        if ("testException".equals(body)) {
            return new ResponseMessage();
        }
        throw new IllegalArgumentException("Must be testException!");
    }

    @RequestMapping("webException")
    public ResponseMessage testWebException(String body) {
        if ("testWebException".equals(body)) {
            return new ResponseMessage();
        }
        throw new WebStatusException("Must be testWebException!");
    }

    public static class ResponseMessage {

        private String subscription = "subscription";
        private String description = "description";

        public String getSubscription() {
            return subscription;
        }

        public void setSubscription(String subscription) {
            this.subscription = subscription;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }
}
