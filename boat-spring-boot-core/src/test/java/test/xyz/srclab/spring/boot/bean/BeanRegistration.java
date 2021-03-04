package test.xyz.srclab.spring.boot.bean;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import xyz.srclab.spring.boot.bean.SpringBeanGenerator;
import xyz.srclab.spring.boot.bean.SpringBeanRegistration;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Component
public class BeanRegistration implements SpringBeanRegistration {

    @NotNull
    @Override
    public Map<String, Object> registryBeans() {
        Map<String, Object> map = new HashMap<>();
        map.put("bean1", "bean1");
        return map;
    }

    @NotNull
    @Override
    public Set<SpringBeanGenerator> registryBeanGenerators() {
        Set<SpringBeanGenerator> set = new HashSet<>();
        set.add(new SpringBeanGenerator() {
            @NotNull
            @Override
            public String name() {
                return "bean2";
            }

            @NotNull
            @Override
            public Object generate() {
                return "bean2";
            }
        });
        return set;
    }
}