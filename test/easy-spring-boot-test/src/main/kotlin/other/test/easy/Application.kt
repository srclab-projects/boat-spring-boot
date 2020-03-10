package other.test.easy

import org.springframework.boot.CommandLineRunner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.testng.Assert
import javax.annotation.Resource

@SpringBootApplication
open class Application : CommandLineRunner {

    @Resource
    private lateinit var testBeanConfigure: TestBeanConfigure

    @Resource
    private lateinit var testBeanPostConfigure: TestBeanPostConfigure

    override fun run(vararg args: String?) {
        testBeanConfigure.printMarkInfo()
        Assert.assertEquals(
            testBeanConfigure.getActualMark("TestBeanConfigure.configureBeanDefinition"),
            testBeanConfigure.generateMark("TestBeanConfigure.configureBeanDefinition")
        )
        testBeanPostConfigure.printMarkInfo()
        Assert.assertEquals(
            testBeanPostConfigure.getActualMark("TestBeanPostConfigure.configureBeforeInitialization"),
            testBeanPostConfigure.generateMark("TestBeanPostConfigure.configureBeforeInitialization")
        )
    }
}

fun main(args: Array<String>) {
    SpringApplication.run(Application::class.java, *args)
}