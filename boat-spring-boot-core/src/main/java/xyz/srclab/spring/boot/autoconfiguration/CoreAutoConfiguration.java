package xyz.srclab.spring.boot.autoconfiguration;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import xyz.srclab.spring.boot.core.CoreBean;
import xyz.srclab.spring.boot.core.CoreBeanProperties;

@Configuration
public class CoreAutoConfiguration {

    @ConditionalOnMissingBean
    @Bean
    public CoreBean coreBean() {
        return new CoreBean();
    }

    @ConfigurationProperties(prefix = "boat.core")
    @Bean("xyz.srclab.spring.boot.core.CoreBeanProperties")
    public CoreBeanProperties coreBeanProperties() {
        return new CoreBeanProperties();
    }
}
