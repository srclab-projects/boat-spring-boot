package other.autoconfigure.test

import org.springframework.boot.CommandLineRunner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
open class Application : CommandLineRunner {

    override fun run(vararg args: String?) {
    }
}

fun main(args: Array<String>) {
    SpringApplication.run(Application::class.java, *args)
}