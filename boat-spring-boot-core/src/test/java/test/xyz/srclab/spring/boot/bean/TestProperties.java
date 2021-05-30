package test.xyz.srclab.spring.boot.bean;

import org.springframework.beans.factory.annotation.Value;

public class TestProperties {

    @Value("123")
    private String bean1;

    @Value("456")
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
