package xyz.srclab.spring.boot.test.test

import org.springframework.boot.SpringBootConfiguration
import org.springframework.context.annotation.ComponentScan

@SpringBootConfiguration
@ComponentScan(basePackages = ["xyz.srclab.spring.boot.test.test"])
open class Config {
}