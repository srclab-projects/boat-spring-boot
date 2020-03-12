package xyz.srclab.spring.boot.bean.match;

import org.springframework.context.ApplicationContext;

import java.util.Map;

public interface BeanMatcher {

    Map<String, Object> match(ApplicationContext applicationContext);
}
