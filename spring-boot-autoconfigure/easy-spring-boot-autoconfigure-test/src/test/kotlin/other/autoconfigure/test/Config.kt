package other.autoconfigure.test

import org.springframework.boot.SpringBootConfiguration
import org.springframework.context.annotation.ComponentScan

@SpringBootConfiguration
@ComponentScan(basePackages = ["other.autoconfigure.test"])
open class Config {
}