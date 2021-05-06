# <span class="image">![Boat Spring Boot](../logo.svg)</span> Boat Spring Boot: SrcLab核心基础Spring Boot库

<span id="author" class="author">Sun Qian</span>  
<span id="email" class="email"><fredsuvn@163.com></span>  

目录

-   [简介](#_简介)
-   [获取](#_获取)
-   [使用](#_使用)
    -   [Core
        (boat-spring-boot-starter)](#_core_boat_spring_boot_starter)
        -   [Bean](#_bean)
        -   [Task](#_task)
        -   [Schedule](#_schedule)
        -   [Exception](#_exception)
    -   [Web
        (boat-spring-boot-web-starter)](#_web_boat_spring_boot_web_starter)
        -   [Exception](#_exception_2)
        -   [Utilities](#_utilities)
-   [共享和联系方式](#_共享和联系方式)
-   [License](#_license)

## 简介

Boat Spring
Boot是一组基于https://github.com/srclab-projects/boat\[Boat\]的spring-boot项目集合.
它在https://github.com/srclab-projects/boat\[Boat\]的基础上,
提供了许多方便快捷的spring-boot接口, 函数和工具.

Boat Spring Boot包括:

-   **boat-spring-boot-core**: 核心和基础的接口, 功能和工具, 比如spring
    bean操作;

-   **boat-spring-boot-test**: 测试依赖管理,
    用来在测试编译和运行范围下引入测试框架;

-   **boat-spring-boot-bom**: Boat Spring Boot Bom.

根据Spring Boot官方推荐, 我们应当使用starter来导入Boat Spring Boot:

-   boat-spring-boot-starter

-   boat-spring-boot-starter-test

-   boat-spring-boot-starter-bom

## 获取

Gradle

    implementation("xyz.srclab.spring.boot:boat-spring-boot-starter:0.0.0")

Maven

    <dependency>
        <groupId>xyz.srclab.spring.boot</groupId>
        <artifactId>boat-spring-boot-starter</artifactId>
        <version>0.0.0</version>
    </dependency>

Source Code

<https://github.com/srclab-projects/boat-spring-boot>

## 使用

### Core (boat-spring-boot-starter)

#### Bean

Bean提供:

-   BeanProperties: bean属性;

-   BeanLifecyclePostProcessor: Spring Bean整个生命周期后置处理器;

-   BeanRegistry: 动态bean注册.

Java Examples

    package sample.java.xyz.srclab.spring.boot.bean;

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
        protected Map<String, Object> registeredSingletons() {
            Map<String, Object> result = new HashMap<>();
            result.put("bean1", "bean1");
            result.put("bean2", "bean2");
            return result;
        }

        @NotNull
        @Override
        protected Set<BeanProperties> registeredBeans() {
            Set<BeanProperties> result = new HashSet<>();
            BeanProperties beanProperties = new BeanProperties();
            beanProperties.setName("myBean");
            beanProperties.setClassName(MyBean.class.getName());
            result.add(beanProperties);
            return result;
        }
    }

    package sample.java.xyz.srclab.spring.boot.bean;

    import org.jetbrains.annotations.NotNull;
    import org.springframework.beans.BeansException;
    import org.springframework.beans.PropertyValues;
    import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
    import org.springframework.beans.factory.support.BeanDefinitionRegistry;
    import org.springframework.stereotype.Component;
    import xyz.srclab.spring.boot.bean.BeanLifecyclePostProcessor;

    import java.util.LinkedList;
    import java.util.List;

    @Component
    public class MyBeanLifecyclePostProcessor implements BeanLifecyclePostProcessor {

        private final List<String> sequence = new LinkedList<>();

        private boolean isPostProcessBeanDefinitionRegistry = false;
        private boolean isPostProcessBeanFactory = false;
        private boolean isPostProcessBeforeInstantiation = false;
        private boolean isPostProcessAfterInstantiation = false;
        private boolean isPostProcessBeforeInitialization = false;
        private boolean isPostProcessAfterInitialization = false;
        private boolean isPostProcessProperties = false;

        @Override
        public void postProcessBeanDefinitionRegistry(@NotNull BeanDefinitionRegistry registry) {
            if (!isPostProcessBeanDefinitionRegistry) {
                sequence.add("postProcessBeanDefinitionRegistry");
                isPostProcessBeanDefinitionRegistry = true;
            }
        }

        @Override
        public void postProcessBeanFactory(@NotNull ConfigurableListableBeanFactory beanFactory) {
            if (!isPostProcessBeanFactory) {
                sequence.add("postProcessBeanFactory");
                isPostProcessBeanFactory = true;
            }
        }

        @Override
        public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
            if (!isPostProcessBeforeInstantiation) {
                sequence.add("postProcessBeforeInstantiation");
                isPostProcessBeforeInstantiation = true;
            }
            return null;
        }

        @Override
        public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
            if (!isPostProcessAfterInstantiation) {
                sequence.add("postProcessAfterInstantiation");
                isPostProcessAfterInstantiation = true;
            }
            return true;
        }

        @Override
        public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
            if (!isPostProcessBeforeInitialization) {
                sequence.add("postProcessBeforeInitialization");
                isPostProcessBeforeInitialization = true;
            }
            return bean;
        }

        @Override
        public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
            if (!isPostProcessAfterInitialization) {
                sequence.add("postProcessAfterInitialization");
                isPostProcessAfterInitialization = true;
            }
            return null;
        }

        @Override
        public PropertyValues postProcessProperties(PropertyValues pvs, Object bean, String beanName) throws BeansException {
            if (!isPostProcessProperties) {
                sequence.add("postProcessProperties");
                isPostProcessProperties = true;
            }
            return null;
        }

        public List<String> getSequence() {
            return sequence;
        }
    }

Kotlin Examples

    package sample.kotlin.xyz.srclab.spring.boot.bean

    import org.springframework.stereotype.Component
    import xyz.srclab.spring.boot.bean.BeanProperties
    import xyz.srclab.spring.boot.bean.BeanRegistry

    @Component
    open class MyBeanRegistry : BeanRegistry() {

        override val registeredSingletons: Map<String, Any>
            get() {
                val result: MutableMap<String, Any> = HashMap()
                result["bean1"] = "bean1"
                result["bean2"] = "bean2"
                return result
            }

        override val registeredBeans: Set<BeanProperties>
            get() {
                val result: MutableSet<BeanProperties> = HashSet()
                val beanProperties = BeanProperties()
                beanProperties.name = "myBean"
                beanProperties.className = MyBean::class.java.name
                result.add(beanProperties)
                return result
            }
    }

    package sample.kotlin.xyz.srclab.spring.boot.bean

    import org.springframework.beans.PropertyValues
    import org.springframework.beans.factory.config.ConfigurableListableBeanFactory
    import org.springframework.beans.factory.support.BeanDefinitionRegistry
    import org.springframework.stereotype.Component
    import xyz.srclab.spring.boot.bean.BeanLifecyclePostProcessor
    import java.util.*

    @Component
    open class MyBeanLifecyclePostProcessor : BeanLifecyclePostProcessor {

        private val sequence: MutableList<String> = LinkedList()
        private var isPostProcessBeanDefinitionRegistry = false
        private var isPostProcessBeanFactory = false
        private var isPostProcessBeforeInstantiation = false
        private var isPostProcessAfterInstantiation = false
        private var isPostProcessBeforeInitialization = false
        private var isPostProcessAfterInitialization = false
        private var isPostProcessProperties = false

        override fun postProcessBeanDefinitionRegistry(registry: BeanDefinitionRegistry) {
            if (!isPostProcessBeanDefinitionRegistry) {
                sequence.add("postProcessBeanDefinitionRegistry")
                isPostProcessBeanDefinitionRegistry = true
            }
        }

        override fun postProcessBeanFactory(beanFactory: ConfigurableListableBeanFactory) {
            if (!isPostProcessBeanFactory) {
                sequence.add("postProcessBeanFactory")
                isPostProcessBeanFactory = true
            }
        }

        override fun postProcessBeforeInstantiation(beanClass: Class<*>, beanName: String): Any? {
            if (!isPostProcessBeforeInstantiation) {
                sequence.add("postProcessBeforeInstantiation")
                isPostProcessBeforeInstantiation = true
            }
            return null
        }

        override fun postProcessAfterInstantiation(bean: Any, beanName: String): Boolean {
            if (!isPostProcessAfterInstantiation) {
                sequence.add("postProcessAfterInstantiation")
                isPostProcessAfterInstantiation = true
            }
            return true
        }

        override fun postProcessBeforeInitialization(bean: Any, beanName: String): Any? {
            if (!isPostProcessBeforeInitialization) {
                sequence.add("postProcessBeforeInitialization")
                isPostProcessBeforeInitialization = true
            }
            return bean
        }

        override fun postProcessAfterInitialization(bean: Any, beanName: String): Any? {
            if (!isPostProcessAfterInitialization) {
                sequence.add("postProcessAfterInitialization")
                isPostProcessAfterInitialization = true
            }
            return null
        }

        override fun postProcessProperties(pvs: PropertyValues, bean: Any, beanName: String): PropertyValues? {
            if (!isPostProcessProperties) {
                sequence.add("postProcessProperties")
                isPostProcessProperties = true
            }
            return null
        }

        fun getSequence(): List<String> {
            return sequence
        }
    }

#### Task

Task提供:

-   TaskPoolProperties: Task线程池属性;

-   TaskExecutors: 快速创建TaskExecutor, 通常使用ThreadPoolProperties;

-   TaskDelegate: Task调度委托.

Java Examples

    package sample.java.xyz.srclab.spring.boot.task;

    import org.springframework.context.annotation.Bean;
    import org.springframework.context.annotation.Configuration;
    import org.springframework.core.task.TaskExecutor;
    import org.springframework.scheduling.annotation.EnableAsync;
    import xyz.srclab.spring.boot.task.TaskExecutors;
    import xyz.srclab.spring.boot.task.TaskPoolProperties;

    @Configuration
    @EnableAsync
    public class MyTaskExecutorConfiguration {

        @Bean
        public TaskExecutor taskExecutor() {
            TaskPoolProperties poolProperties = new TaskPoolProperties();
            poolProperties.setThreadNamePrefix("6666");
            return TaskExecutors.newTaskExecutor(poolProperties);
        }
    }

Kotlin Examples

    package sample.kotlin.xyz.srclab.spring.boot.task

    import org.springframework.context.annotation.Bean
    import org.springframework.context.annotation.Configuration
    import org.springframework.core.task.TaskExecutor
    import org.springframework.scheduling.annotation.EnableAsync
    import xyz.srclab.spring.boot.task.TaskPoolProperties
    import xyz.srclab.spring.boot.task.toTaskExecutor

    @Configuration
    @EnableAsync
    open class MyTaskExecutorConfigurationKt {

        @Bean
        open fun taskExecutor(): TaskExecutor {
            val poolProperties = TaskPoolProperties()
            poolProperties.threadNamePrefix = "6666"
            return poolProperties.toTaskExecutor()
        }
    }

#### Schedule

Schedule提供:

-   ScheduledPoolProperties: 调度线程池属性;

-   TaskSchedulers: 快速创建TaskScheduler,
    通常使用ScheduledPoolProperties.

Java Examples

    package sample.java.xyz.srclab.spring.boot.schedule;

    import org.springframework.context.annotation.Bean;
    import org.springframework.context.annotation.Configuration;
    import org.springframework.scheduling.TaskScheduler;
    import org.springframework.scheduling.annotation.EnableScheduling;
    import xyz.srclab.spring.boot.schedule.ScheduledPoolProperties;
    import xyz.srclab.spring.boot.schedule.TaskSchedulers;

    @Configuration
    @EnableScheduling
    public class MyTaskSchedulerConfiguration {

        @Bean
        public TaskScheduler taskScheduler() {
            ScheduledPoolProperties poolProperties = new ScheduledPoolProperties();
            poolProperties.setThreadNamePrefix("6666");
            return TaskSchedulers.newTaskScheduler(poolProperties);
        }
    }

Kotlin Examples

    package sample.kotlin.xyz.srclab.spring.boot.schedule

    import org.springframework.context.annotation.Bean
    import org.springframework.context.annotation.Configuration
    import org.springframework.scheduling.TaskScheduler
    import org.springframework.scheduling.annotation.EnableScheduling
    import xyz.srclab.spring.boot.schedule.ScheduledPoolProperties
    import xyz.srclab.spring.boot.schedule.toTaskScheduler

    @Configuration
    @EnableScheduling
    open class MyTaskSchedulerConfiguration {

        @Bean
        open fun taskScheduler(): TaskScheduler {
            val poolProperties = ScheduledPoolProperties()
            poolProperties.threadNamePrefix = "6666"
            return poolProperties.toTaskScheduler()
        }
    }

#### Exception

异常包提供:

-   EnableExceptionService: 开启异常服务的注解;

-   ExceptionStatusService: 自动注入的全局异常处理,
    使用ExceptionStateHandler;

-   ExceptionStatusHandler: 用户定义的bean, 用来装换异常到State.

Java Examples

    package sample.java.xyz.srclab.spring.boot.exception;

    import org.springframework.boot.test.context.SpringBootTest;
    import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
    import org.testng.Assert;
    import org.testng.annotations.Test;
    import xyz.srclab.common.exception.ExceptionStatus;
    import xyz.srclab.spring.boot.exception.EnableExceptionService;
    import xyz.srclab.spring.boot.exception.ExceptionStatusService;

    import javax.annotation.Resource;

    @SpringBootTest(classes = Starter.class)
    @EnableExceptionService
    public class ExceptionServiceSample extends AbstractTestNGSpringContextTests {

        @Resource
        private ExceptionStatusService exceptionStatusService;

        @Test
        public void testExceptionStateService() {
            ExceptionStatus runtime = exceptionStatusService.toState(new RuntimeException());
            Assert.assertEquals(runtime.code(), "102");
            ExceptionStatus throwable = exceptionStatusService.toState(new Exception());
            Assert.assertEquals(throwable.code(), "101");
        }
    }

    package sample.java.xyz.srclab.spring.boot.exception;

    import org.jetbrains.annotations.NotNull;
    import org.springframework.stereotype.Component;
    import xyz.srclab.common.exception.ExceptionStatus;
    import xyz.srclab.spring.boot.exception.ExceptionStatusHandler;

    @Component
    public class RuntimeExceptionStatusHandler implements ExceptionStatusHandler<RuntimeException, ExceptionStatus> {

        @NotNull
        @Override
        public Class<RuntimeException> supportedExceptionType() {
            return RuntimeException.class;
        }

        @NotNull
        @Override
        public ExceptionStatus handle(@NotNull RuntimeException exception) {
            return ExceptionStatus.of("102");
        }
    }

    package sample.java.xyz.srclab.spring.boot.exception;

    import org.jetbrains.annotations.NotNull;
    import org.springframework.stereotype.Component;
    import xyz.srclab.common.exception.ExceptionStatus;
    import xyz.srclab.spring.boot.exception.ExceptionStatusHandler;

    @Component
    public class ThrowableStatusHandler implements ExceptionStatusHandler<Throwable, ExceptionStatus> {

        @NotNull
        @Override
        public Class<Throwable> supportedExceptionType() {
            return Throwable.class;
        }

        @NotNull
        @Override
        public ExceptionStatus handle(@NotNull Throwable throwable) {
            return ExceptionStatus.of("101");
        }
    }

Kotlin Examples

    package sample.kotlin.xyz.srclab.spring.boot.exception

    import org.springframework.boot.autoconfigure.SpringBootApplication
    import org.springframework.boot.test.context.SpringBootTest
    import org.springframework.stereotype.Component
    import org.springframework.test.context.testng.AbstractTestNGSpringContextTests
    import org.testng.Assert
    import org.testng.annotations.Test
    import xyz.srclab.common.exception.ExceptionStatus
    import xyz.srclab.spring.boot.exception.EnableExceptionService
    import xyz.srclab.spring.boot.exception.ExceptionStatusHandler
    import xyz.srclab.spring.boot.exception.ExceptionStatusService
    import javax.annotation.Resource

    @SpringBootTest(classes = [Starter::class])
    @EnableExceptionService
    class ExceptionServiceSample : AbstractTestNGSpringContextTests() {

        @Resource
        private lateinit var exceptionStatusService: ExceptionStatusService

        @Test
        fun testExceptionStateService() {
            val runtime = exceptionStatusService.toState<ExceptionStatus>(RuntimeException())
            Assert.assertEquals(runtime.code, "102")
            val throwable = exceptionStatusService.toState<ExceptionStatus>(Exception())
            Assert.assertEquals(throwable.code, "101")
        }
    }

    @Component
    open class RuntimeExceptionStatusHandler :
        ExceptionStatusHandler<RuntimeException, ExceptionStatus> {
        override val supportedExceptionType: Class<RuntimeException> = RuntimeException::class.java
        override fun handle(e: RuntimeException): ExceptionStatus {
            return ExceptionStatus.of("102")
        }
    }

    @Component
    open class ThrowableStatusHandler : ExceptionStatusHandler<Throwable, ExceptionStatus> {
        override val supportedExceptionType: Class<Throwable> = Throwable::class.java
        override fun handle(e: Throwable): ExceptionStatus {
            return ExceptionStatus.of("101")
        }
    }

    @SpringBootApplication
    open class Starter

### Web (boat-spring-boot-web-starter)

#### Exception

Web异常提供:

-   EnableWebExceptionService: 开启web异常服务的注解;

-   WebExceptionService: 自动注入的全局web异常处理服务,
    使用WebExceptionHandler;

-   WebExceptionHandler: 用户定义的处理异常的bean,
    将异常转成返回值ResponseEntity.

Java Examples

    package sample.java.xyz.srclab.spring.boot.web.exception;

    import org.slf4j.Logger;
    import org.slf4j.LoggerFactory;
    import org.springframework.boot.test.context.SpringBootTest;
    import org.springframework.boot.test.web.client.TestRestTemplate;
    import org.springframework.boot.web.server.LocalServerPort;
    import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
    import org.testng.Assert;
    import org.testng.annotations.Test;
    import xyz.srclab.common.exception.ExceptionStatus;
    import xyz.srclab.common.serialize.json.JsonSerials;
    import xyz.srclab.spring.boot.web.exception.EnableWebExceptionService;

    import javax.annotation.Resource;

    @SpringBootTest(
        classes = Starter.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
    )
    @EnableWebExceptionService
    public class WebExceptionSample extends AbstractTestNGSpringContextTests {

        private static final Logger logger = LoggerFactory.getLogger(WebExceptionSample.class);

        @LocalServerPort
        private int port;

        @Resource
        private TestRestTemplate restTemplate;

        @Test
        public void testException() {
            String result = restTemplate.getForObject(
                "http://localhost:" + port + "/test/exception?body=testException",
                String.class
            );
            logger.info("/test/exception?body=testException: " + result);
            Assert.assertEquals(result, "testException");

            result = restTemplate.getForObject(
                "http://localhost:" + port + "/test/exception?body=testException0",
                String.class
            );
            logger.info("/test/exception?body=testException: " + result);
            Assert.assertEquals(result, JsonSerials.toJsonString(ExceptionStatus.of("102")));
        }
    }

    package sample.java.xyz.srclab.spring.boot.web.exception;

    import org.springframework.web.bind.annotation.RequestMapping;
    import org.springframework.web.bind.annotation.RestController;

    @RequestMapping("test")
    @RestController
    public class TestController {

        @RequestMapping("exception")
        public String testException(String body) {
            if ("testException".equals(body)) {
                return body;
            }
            throw new IllegalArgumentException("Must be testException!");
        }
    }

    package sample.java.xyz.srclab.spring.boot.web.exception;

    import org.jetbrains.annotations.NotNull;
    import org.springframework.http.HttpStatus;
    import org.springframework.http.ResponseEntity;
    import org.springframework.stereotype.Component;
    import xyz.srclab.common.exception.ExceptionStatus;
    import xyz.srclab.spring.boot.web.exception.WebExceptionHandler;

    @Component
    public class RuntimeExceptionHandler implements WebExceptionHandler<RuntimeException> {

        @NotNull
        @Override
        public Class<RuntimeException> supportedExceptionType() {
            return RuntimeException.class;
        }

        @NotNull
        @Override
        public ResponseEntity<ExceptionStatus> handle(@NotNull RuntimeException exception) {
            return new ResponseEntity<>(ExceptionStatus.of("102"), HttpStatus.OK);
        }
    }

    package sample.java.xyz.srclab.spring.boot.web.exception;

    import org.jetbrains.annotations.NotNull;
    import org.springframework.http.HttpStatus;
    import org.springframework.http.ResponseEntity;
    import org.springframework.stereotype.Component;
    import xyz.srclab.common.exception.ExceptionStatus;
    import xyz.srclab.spring.boot.web.exception.WebExceptionHandler;

    @Component
    public class ThrowableHandler implements WebExceptionHandler<Throwable> {

        @NotNull
        @Override
        public Class<Throwable> supportedExceptionType() {
            return Throwable.class;
        }

        @NotNull
        @Override
        public ResponseEntity<ExceptionStatus> handle(@NotNull Throwable throwable) {
            return new ResponseEntity<>(ExceptionStatus.of("101"), HttpStatus.OK);
        }
    }

Kotlin Examples

    package sample.kotlin.xyz.srclab.spring.boot.web.exception

    import org.slf4j.LoggerFactory
    import org.springframework.boot.autoconfigure.SpringBootApplication
    import org.springframework.boot.test.context.SpringBootTest
    import org.springframework.boot.test.web.client.TestRestTemplate
    import org.springframework.boot.web.server.LocalServerPort
    import org.springframework.http.HttpStatus
    import org.springframework.http.ResponseEntity
    import org.springframework.stereotype.Component
    import org.springframework.test.context.testng.AbstractTestNGSpringContextTests
    import org.springframework.web.bind.annotation.RequestMapping
    import org.springframework.web.bind.annotation.RestController
    import org.testng.Assert
    import org.testng.annotations.Test
    import xyz.srclab.common.exception.ExceptionStatus
    import xyz.srclab.common.serialize.json.toJsonString
    import xyz.srclab.spring.boot.web.exception.EnableWebExceptionService
    import xyz.srclab.spring.boot.web.exception.WebExceptionHandler
    import javax.annotation.Resource

    @SpringBootTest(classes = [Starter::class], webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
    @EnableWebExceptionService
    class WebExceptionSample : AbstractTestNGSpringContextTests() {

        @LocalServerPort
        private val port = 0

        @Resource
        private val restTemplate: TestRestTemplate? = null

        @Test
        fun testException() {
            var result = restTemplate!!.getForObject(
                "http://localhost:$port/test/exception?body=testException",
                String::class.java
            )
            Companion.logger.info("/test/exception?body=testException: $result")
            Assert.assertEquals(result, "testException")
            result = restTemplate.getForObject(
                "http://localhost:$port/test/exception?body=testException0",
                String::class.java
            )
            Companion.logger.info("/test/exception?body=testException: $result")
            Assert.assertEquals(result, ExceptionStatus.of("102").toJsonString())
        }

        companion object {
            private val logger = LoggerFactory.getLogger(WebExceptionSample::class.java)
        }
    }

    @RequestMapping("test")
    @RestController
    open class TestController {

        @RequestMapping("exception")
        open fun testException(body: String): String {
            if (body == "testException") {
                return body
            }
            throw IllegalArgumentException("Must be testException!")
        }
    }

    @Component
    open class RuntimeExceptionStatusHandler :
        WebExceptionHandler<RuntimeException> {
        override val supportedExceptionType: Class<RuntimeException> = RuntimeException::class.java
        override fun handle(e: RuntimeException): ResponseEntity<ExceptionStatus> {
            return ResponseEntity(ExceptionStatus.of("102"), HttpStatus.OK)
        }
    }

    @Component
    open class ThrowableStatusHandler : WebExceptionHandler<Throwable> {
        override val supportedExceptionType: Class<Throwable> = Throwable::class.java
        override fun handle(e: Throwable): ResponseEntity<ExceptionStatus> {
            return ResponseEntity(ExceptionStatus.of("101"), HttpStatus.OK)
        }
    }

    @SpringBootApplication
    open class Starter

#### Utilities

一些工具类也在web中提供:

-   WebExceptions: 提供异常相关的工具方法;

-   WebServlets: 提供Servlet工具, 比如针对ServletRequest,
    ServletInputStream等的快速构建.

## 共享和联系方式

-   <fredsuvn@163.com>

-   <https://github.com/srclab-projects/boat-spring-boot>

-   QQ group: 1037555759

## License

[Apache 2.0 license](https://www.apache.org/licenses/LICENSE-2.0.html)
