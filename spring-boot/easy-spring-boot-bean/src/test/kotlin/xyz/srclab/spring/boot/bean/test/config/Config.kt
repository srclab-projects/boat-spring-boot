package xyz.srclab.spring.boot.bean.test.config

import org.springframework.boot.SpringBootConfiguration
import org.springframework.context.annotation.ComponentScan

@SpringBootConfiguration
@ComponentScan(basePackages = ["xyz.srclab.spring.boot.bean.test"])
open class Config {
}