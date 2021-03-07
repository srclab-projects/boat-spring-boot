package test.xyz.srclab.spring.boot.bean;

import javax.annotation.Resource;

public class TestBean {

    @Resource
    private String bean1;

    @Resource
    private String bean2;

    public String getBeanString() {
        return bean1 + bean2;
    }
}
