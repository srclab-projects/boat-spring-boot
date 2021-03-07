package test.xyz.srclab.spring.boot.bean;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "boat.test")
@Component
public class TestProperties {

    private String bean1;
    private String bean2;

    public String getBean1() {
        return bean1;
    }

    public void setBean1(String bean1) {
        this.bean1 = bean1;
    }

    public String getBean2() {
        return bean2;
    }

    public void setBean2(String bean2) {
        this.bean2 = bean2;
    }
}
