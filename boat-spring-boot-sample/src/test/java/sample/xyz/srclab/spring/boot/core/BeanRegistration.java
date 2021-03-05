package sample.xyz.srclab.spring.boot.core;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import xyz.srclab.spring.boot.bean.SpringBeanRegistration;
import xyz.srclab.spring.boot.bean.SpringSingletonGenerator;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Component
public class BeanRegistration implements SpringBeanRegistration {

    @NotNull
    @Override
    public Map<String, Object> registerSingletons() {
        Map<String, Object> map = new HashMap<>();
        map.put("bean1", "bean1");
        return map;
    }

    @NotNull
    @Override
    public Set<SpringSingletonGenerator> registerSingletonGenerators() {
        Set<SpringSingletonGenerator> set = new HashSet<>();
        set.add(new SpringSingletonGenerator() {
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