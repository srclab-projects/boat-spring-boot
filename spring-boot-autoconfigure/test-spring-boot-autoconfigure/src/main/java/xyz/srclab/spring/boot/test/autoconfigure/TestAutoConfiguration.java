package xyz.srclab.spring.boot.test.autoconfigure;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;

@ConditionalOnMissingBean(TestAutoConfiguration.class)
public class TestAutoConfiguration {
}
