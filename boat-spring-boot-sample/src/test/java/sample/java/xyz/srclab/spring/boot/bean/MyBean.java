package sample.java.xyz.srclab.spring.boot.bean;

import org.springframework.context.annotation.DependsOn;

import javax.annotation.Resource;

@DependsOn("myBeanRegistry")
public class MyBean {

    @Resource
    private String bean1;

    @Resource
    private String bean2;

    public String getBeanString() {
        return bean1 + bean2;
    }
}
