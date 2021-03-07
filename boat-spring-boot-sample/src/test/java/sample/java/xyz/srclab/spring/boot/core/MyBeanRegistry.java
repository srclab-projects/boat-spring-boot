package sample.java.xyz.srclab.spring.boot.core;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import xyz.srclab.spring.boot.bean.BeanProperties;
import xyz.srclab.spring.boot.bean.BeanRegistry;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Component
public class MyBeanRegistry extends BeanRegistry {

    @NotNull
    @Override
    protected Map<String, Object> registerSingletons() {
        Map<String, Object> result = new HashMap<>();
        result.put("bean1", "bean1");
        result.put("bean2", "bean2");
        return result;
    }

    @NotNull
    @Override
    protected Set<BeanProperties> registerBeans() {
        Set<BeanProperties> result = new HashSet<>();
        BeanProperties beanProperties = new BeanProperties();
        beanProperties.setName("myBean");
        beanProperties.setClassName(MyBean.class.getName());
        result.add(beanProperties);
        return result;
    }
}