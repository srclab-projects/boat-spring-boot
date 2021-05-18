# <span class="image">![Boat Spring Boot](../logo.svg)</span> Boat Spring Boot: SrcLab Core Libraries for Spring Boot

<span id="author" class="author">Sun Qian</span>  
<span id="email" class="email"><fredsuvn@163.com></span>  

Table of Contents

-   [Introduction](#_introduction)
-   [Getting](#_getting)
-   [Usage](#_usage)
    -   [Core
        (boat-spring-boot-starter)](#_core_boat_spring_boot_starter)
        -   [Lang](#_lang)
        -   [Bean](#_bean)
        -   [Message](#_message)
        -   [Task](#_task)
        -   [Schedule](#_schedule)
        -   [Exception](#_exception)
    -   [Web
        (boat-spring-boot-web-starter)](#_web_boat_spring_boot_web_starter)
        -   [Exception](#_exception_2)
        -   [Message](#_message_2)
        -   [Utilities](#_utilities)
-   [Contribution and Contact](#_contribution_and_contact)
-   [License](#_license)

## Introduction

Boat Spring Boot is a set of spring-boot projects with
[Boat](https://github.com/srclab-projects/boat). It provides many fast
and convenient interfaces, functions and utilities adapted to
spring-boot with [Boat](https://github.com/srclab-projects/boat).

Boat Spring Boot includes:

-   **boat-spring-boot-core**: Core and basic interfaces, functions and
    utilities, such as spring bean operation;

-   **boat-spring-boot-test**: Test dependencies management, to import
    common test libraries in test scope;

-   **boat-spring-boot-bom**: Boat Spring Boot Bom.

Following the official recommendation of Spring Boot, we should use
starter to import Boat Spring Boot:

-   boat-spring-boot-starter

-   boat-spring-boot-starter-test

-   boat-spring-boot-starter-bom

## Getting

Gradle

    implementation("xyz.srclab.spring.boot:boat-spring-boot-starter:0.0.1")

Maven

    <dependency>
        <groupId>xyz.srclab.spring.boot</groupId>
        <artifactId>boat-spring-boot-starter</artifactId>
        <version>0.0.1</version>
    </dependency>

Source Code

<https://github.com/srclab-projects/boat-spring-boot>

## Usage

### Core (boat-spring-boot-starter)

#### Lang

Lang package provides base interfaces, functions and utilities for
spring-boot:

-   EncodeString: Represents encode string may be encoded and encrypted
    as configure property.

Java Examples

    package sample.java.xyz.srclab.spring.boot.lang;

    import org.slf4j.Logger;
    import org.slf4j.LoggerFactory;
    import org.springframework.beans.factory.annotation.Value;
    import org.springframework.boot.test.context.SpringBootTest;
    import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
    import org.testng.Assert;
    import org.testng.annotations.Test;
    import xyz.srclab.common.codec.aes.AesKeys;
    import xyz.srclab.spring.boot.lang.EncodeString;

    import javax.crypto.SecretKey;

    @SpringBootTest(classes = Starter.class)
    //@ContextConfiguration(classes = {TestStarter.class})
    public class LangSample extends AbstractTestNGSpringContextTests {

        private static final Logger logger = LoggerFactory.getLogger(LangSample.class);

        @Value("AES,BASE64:rliqBhMdiKQDcH8lqNZdIg==")
        private EncodeString encodeString;

        @Test
        public void testEncodeString() {
            logger.info("encodeString: {}", encodeString);
            SecretKey key = AesKeys.newKey("123");
            Assert.assertEquals(encodeString.decodeString(key), "some password");
        }
    }

Kotlin Examples

    package sample.kotlin.xyz.srclab.spring.boot.lang

    import org.slf4j.LoggerFactory
    import org.springframework.beans.factory.annotation.Value
    import org.springframework.boot.autoconfigure.SpringBootApplication
    import org.springframework.boot.test.context.SpringBootTest
    import org.springframework.test.context.testng.AbstractTestNGSpringContextTests
    import org.testng.Assert
    import org.testng.annotations.Test
    import xyz.srclab.common.codec.aes.toAesKey
    import xyz.srclab.spring.boot.lang.EncodeString

    @SpringBootTest(classes = [Starter::class])
    open class LangSample : AbstractTestNGSpringContextTests() {

        @Value("AES,BASE64:rliqBhMdiKQDcH8lqNZdIg==")
        private lateinit var encodeString: EncodeString

        @Test
        fun testEncodeString() {
            log.info("encodeString: {}", encodeString)
            val key = "123".toAesKey()
            Assert.assertEquals(encodeString.decodeString(key), "some password")
        }

        companion object {
            private val log = LoggerFactory.getLogger(LangSample::class.java)
        }
    }

    @SpringBootApplication
    open class Starter

#### Bean

Bean package provides:

-   BeanProperties: Properties for bean;

-   BeanLifecyclePostProcessor: Spring Bean lifecycle post processor;

-   BeanRegistry: Dynamic bean registry.

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

#### Message

Message package provides:

-   ReqMessage: Convenient request message definition;

-   RespMessage: Convenient response message definition.

#### Task

Task package provides:

-   TaskPoolProperties: Properties for task thread pool;

-   TaskExecutors: Help fast create TaskExecutor with
    ThreadPoolProperties;

-   TaskDelegate: Task execution delegate.

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

Schedule package provides:

-   ScheduledPoolProperties: Properties for scheduled thread pool;

-   TaskSchedulers: Help fast create TaskScheduler with
    ScheduledPoolProperties.

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

Exception package provides:

-   EnableExceptionHandlingService: Annotation to enable exception
    handling service;

-   ExceptionHandlingService: Autowired global exception handling
    service, see its javadoc for more detail;

-   ExceptionHandler: Interface to handle exception for
    ExceptionHandlingService.

Java Examples

    package sample.java.xyz.srclab.spring.boot.exception;

    import org.springframework.boot.test.context.SpringBootTest;
    import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
    import org.testng.Assert;
    import org.testng.annotations.Test;
    import xyz.srclab.common.exception.ExceptionStatus;
    import xyz.srclab.spring.boot.exception.EnableExceptionHandlingService;
    import xyz.srclab.spring.boot.exception.ExceptionHandlingService;

    import javax.annotation.Resource;

    @SpringBootTest(classes = Starter.class)
    @EnableExceptionHandlingService
    public class ExceptionServiceSample extends AbstractTestNGSpringContextTests {

        @Resource
        private ExceptionHandlingService exceptionHandlingService;

        @Test
        public void testExceptionStateService() {
            ExceptionStatus runtime = exceptionHandlingService.toState(new RuntimeException());
            Assert.assertEquals(runtime.code(), "102");
            ExceptionStatus throwable = exceptionHandlingService.toState(new Exception());
            Assert.assertEquals(throwable.code(), "101");
        }
    }

    package sample.java.xyz.srclab.spring.boot.exception;

    import org.jetbrains.annotations.NotNull;
    import org.springframework.stereotype.Component;
    import xyz.srclab.common.exception.ExceptionStatus;
    import xyz.srclab.spring.boot.exception.ExceptionHandler;

    @Component
    public class ThrowableStatusHandler implements ExceptionHandler<Throwable, ExceptionStatus> {

        @NotNull
        public Class<Throwable> supportedType() {
            return Throwable.class;
        }

        @NotNull
        @Override
        public ExceptionStatus handle(@NotNull Throwable throwable) {
            return ExceptionStatus.of("101");
        }
    }

    package sample.java.xyz.srclab.spring.boot.exception;

    import org.jetbrains.annotations.NotNull;
    import org.springframework.stereotype.Component;
    import xyz.srclab.common.exception.ExceptionStatus;
    import xyz.srclab.spring.boot.exception.ExceptionHandler;

    @Component
    public class RuntimeExceptionStatusHandler implements ExceptionHandler<RuntimeException, ExceptionStatus> {

        @NotNull
        public Class<RuntimeException> supportedType() {
            return RuntimeException.class;
        }

        @NotNull
        @Override
        public ExceptionStatus handle(@NotNull RuntimeException exception) {
            return ExceptionStatus.of("102");
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
    import xyz.srclab.spring.boot.exception.EnableExceptionHandlingService
    import xyz.srclab.spring.boot.exception.ExceptionHandler
    import xyz.srclab.spring.boot.exception.ExceptionHandlingService
    import javax.annotation.Resource

    @SpringBootTest(classes = [Starter::class])
    @EnableExceptionHandlingService
    class ExceptionServiceSample : AbstractTestNGSpringContextTests() {

        @Resource
        private lateinit var exceptionHandlingService: ExceptionHandlingService

        @Test
        fun testExceptionStateService() {
            val runtime = exceptionHandlingService.toState<ExceptionStatus>(RuntimeException())
            Assert.assertEquals(runtime.code, "102")
            val throwable = exceptionHandlingService.toState<ExceptionStatus>(Exception())
            Assert.assertEquals(throwable.code, "101")
        }
    }

    @Component
    open class RuntimeExceptionStatusHandler :
        ExceptionHandler<RuntimeException, ExceptionStatus> {
        override val supportedType: Class<RuntimeException> = RuntimeException::class.java
        override fun handle(e: RuntimeException): ExceptionStatus {
            return ExceptionStatus.of("102")
        }
    }

    @Component
    open class ThrowableStatusHandler : ExceptionHandler<Throwable, ExceptionStatus> {
        override val supportedType: Class<Throwable> = Throwable::class.java
        override fun handle(e: Throwable): ExceptionStatus {
            return ExceptionStatus.of("101")
        }
    }

    @SpringBootApplication
    open class Starter

### Web (boat-spring-boot-web-starter)

#### Exception

Web exception package provides:

-   EnableWebExceptionService: Annotation to enable web exception
    service;

-   WebExceptionService: Autowired global web exception service, see its
    javadoc for more detail;

-   WebExceptionResponseHandler: Interface used to convert exception to
    ResponseEntity, work for WebExceptionService;

-   WebStatusException: Convenient exception for web.

Java Examples

    package sample.java.xyz.srclab.spring.boot.web.exception;

    import org.slf4j.Logger;
    import org.slf4j.LoggerFactory;
    import org.springframework.boot.test.context.SpringBootTest;
    import org.springframework.boot.test.web.client.TestRestTemplate;
    import org.springframework.boot.web.server.LocalServerPort;
    import org.springframework.http.HttpStatus;
    import org.springframework.http.ResponseEntity;
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
            logger.info("/test/exception?body=testException: {}", result);
            Assert.assertEquals(result, JsonSerials.toJsonString(new TestController.ResponseMessage()));

            result = restTemplate.getForObject(
                "http://localhost:" + port + "/test/exception?body=testException0",
                String.class
            );
            logger.info("/test/exception?body=testException0: {}", result);
            Assert.assertEquals(result, JsonSerials.toJsonString(ExceptionStatus.of("102")));

            ResponseEntity<String> entity = restTemplate.getForEntity(
                "http://localhost:" + port + "/test/webException?body=testWebException0",
                String.class
            );
            logger.info("/test/webException?body=testWebException0: {}", result);
            Assert.assertEquals(entity.getStatusCode(), HttpStatus.INTERNAL_SERVER_ERROR);
            Assert.assertEquals(entity.getBody(), JsonSerials.toJsonString(ExceptionStatus.of("103")));
        }
    }

    package sample.java.xyz.srclab.spring.boot.web.exception;

    import org.springframework.web.bind.annotation.RequestMapping;
    import org.springframework.web.bind.annotation.RestController;
    import xyz.srclab.spring.boot.web.exception.WebStatusException;

    @RequestMapping("test")
    @RestController
    public class TestController {

        @RequestMapping("exception")
        public ResponseMessage testException(String body) {
            if ("testException".equals(body)) {
                return new ResponseMessage();
            }
            throw new IllegalArgumentException("Must be testException!");
        }

        @RequestMapping("webException")
        public ResponseMessage testWebException(String body) {
            if ("testWebException".equals(body)) {
                return new ResponseMessage();
            }
            throw new WebStatusException("Must be testWebException!");
        }

        public static class ResponseMessage {

            private String subscription = "subscription";
            private String description = "description";

            public String getSubscription() {
                return subscription;
            }

            public void setSubscription(String subscription) {
                this.subscription = subscription;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }
        }
    }

    package sample.java.xyz.srclab.spring.boot.web.exception;

    import org.jetbrains.annotations.NotNull;
    import org.springframework.http.HttpStatus;
    import org.springframework.http.ResponseEntity;
    import org.springframework.stereotype.Component;
    import xyz.srclab.common.exception.ExceptionStatus;
    import xyz.srclab.spring.boot.web.exception.WebExceptionResponseHandler;

    @Component
    public class ThrowableHandler implements WebExceptionResponseHandler<Throwable> {

        @NotNull
        public Class<Throwable> supportedType() {
            return Throwable.class;
        }

        @NotNull
        @Override
        public ResponseEntity<ExceptionStatus> handle(@NotNull Throwable throwable) {
            return new ResponseEntity<>(ExceptionStatus.of("101"), HttpStatus.OK);
        }
    }

    package sample.java.xyz.srclab.spring.boot.web.exception;

    import org.jetbrains.annotations.NotNull;
    import org.springframework.http.HttpStatus;
    import org.springframework.http.ResponseEntity;
    import org.springframework.stereotype.Component;
    import xyz.srclab.common.exception.ExceptionStatus;
    import xyz.srclab.spring.boot.web.exception.WebExceptionResponseHandler;

    @Component
    public class RuntimeExceptionHandler implements WebExceptionResponseHandler<RuntimeException> {

        @NotNull
        public Class<RuntimeException> supportedType() {
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
    import org.springframework.http.ResponseEntity;
    import org.springframework.stereotype.Component;
    import xyz.srclab.common.exception.ExceptionStatus;
    import xyz.srclab.spring.boot.web.exception.WebExceptionResponseHandler;
    import xyz.srclab.spring.boot.web.exception.WebStatusException;

    @Component
    public class WebStatusExceptionHandler implements WebExceptionResponseHandler<WebStatusException> {

        @NotNull
        public Class<WebStatusException> supportedType() {
            return WebStatusException.class;
        }

        @NotNull
        @Override
        public ResponseEntity<ExceptionStatus> handle(@NotNull WebStatusException exception) {
            return new ResponseEntity<>(ExceptionStatus.of("103"), exception.httpStatus());
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
    import xyz.srclab.spring.boot.web.exception.WebExceptionResponseHandler
    import xyz.srclab.spring.boot.web.exception.WebStatusException
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
            log.info("/test/exception?body=testException: {}", result)
            Assert.assertEquals(
                result,
                TestController.ResponseMessage().toJsonString()
            )

            result = restTemplate.getForObject(
                "http://localhost:$port/test/exception?body=testException0",
                String::class.java
            )
            log.info("/test/exception?body=testException0: {}", result)
            Assert.assertEquals(result, ExceptionStatus.of("102").toJsonString())

            val entity = restTemplate.getForEntity(
                "http://localhost:$port/test/webException?body=testWebException0",
                String::class.java
            )
            log.info("/test/webException?body=testWebException0: {}", result)
            Assert.assertEquals(entity.statusCode, HttpStatus.INTERNAL_SERVER_ERROR)
            Assert.assertEquals(entity.body, ExceptionStatus.of("103").toJsonString())
        }

        companion object {
            private val log = LoggerFactory.getLogger(WebExceptionSample::class.java)
        }
    }

    @RequestMapping("test")
    @RestController
    class TestController {

        @RequestMapping("exception")
        fun testException(body: String): ResponseMessage {
            if ("testException" == body) {
                return ResponseMessage()
            }
            throw IllegalArgumentException("Must be testException!")
        }

        @RequestMapping("webException")
        fun testWebException(body: String): ResponseMessage {
            if ("testWebException" == body) {
                return ResponseMessage()
            }
            throw WebStatusException("Must be testWebException!")
        }

        class ResponseMessage {
            var subscription = "subscription"
            var description = "description"
        }
    }

    @Component
    open class RuntimeExceptionStatusHandler :
        WebExceptionResponseHandler<RuntimeException> {
        override val supportedType: Class<RuntimeException> = RuntimeException::class.java
        override fun handle(e: RuntimeException): ResponseEntity<ExceptionStatus> {
            return ResponseEntity(ExceptionStatus.of("102"), HttpStatus.OK)
        }
    }

    @Component
    open class ThrowableStatusHandler : WebExceptionResponseHandler<Throwable> {
        override val supportedType: Class<Throwable> = Throwable::class.java
        override fun handle(e: Throwable): ResponseEntity<ExceptionStatus> {
            return ResponseEntity(ExceptionStatus.of("101"), HttpStatus.OK)
        }
    }

    @Component
    class WebStatusExceptionHandler : WebExceptionResponseHandler<WebStatusException> {
        override val supportedType: Class<WebStatusException> = WebStatusException::class.java
        override fun handle(e: WebStatusException): ResponseEntity<ExceptionStatus> {
            return ResponseEntity(ExceptionStatus.of("103"), e.httpStatus)
        }
    }

    @SpringBootApplication
    open class Starter

#### Message

Web message package provides:

-   HttpReqMessage, HttpRespMessage: Convenient http message definition;

-   HttpReqMessageResolver: Handler method arguments resolver for
    HttpReqMessage;

-   MessageProperties: Properties for http messages;

-   EnableHttpReqMessageResolving: Enable to resolve parameter of
    HttpReqMessage type, see its javadoc for more detail.

Java Examples

    package sample.java.xyz.srclab.spring.boot.web.message;

    import org.springframework.web.bind.annotation.RequestMapping;
    import org.springframework.web.bind.annotation.RestController;
    import org.springframework.web.servlet.ModelAndView;
    import xyz.srclab.spring.boot.web.message.HttpReqMessage;
    import xyz.srclab.spring.boot.web.servlet.WebServlets;

    import javax.servlet.http.HttpServletRequest;

    @RequestMapping("test")
    @RestController
    public class MessageController {

        @RequestMapping("internal/message")
        public RespBody testMessage(HttpReqMessage<ReqBody> httpReqMessage) {
            RespBody respBody = new RespBody();
            respBody.setResp1(httpReqMessage.getBody().getReq1());
            respBody.setResp2(httpReqMessage.getBody().getReq2());
            return respBody;
        }

        @RequestMapping("message")
        public ModelAndView testMessage(ReqBody reqBody, HttpServletRequest servletRequest) {
            HttpReqMessage<ReqBody> httpReqMessage = HttpReqMessage.newHttpReqMessage();
            httpReqMessage.setMetadata(WebServlets.toHttpHeaders(servletRequest));
            httpReqMessage.setBody(reqBody);
            servletRequest.setAttribute("httpReqMessage", httpReqMessage);
            return new ModelAndView("internal/message");
        }
    }

    package sample.java.xyz.srclab.spring.boot.web.message;

    import org.slf4j.Logger;
    import org.slf4j.LoggerFactory;
    import org.springframework.boot.test.context.SpringBootTest;
    import org.springframework.boot.test.web.client.TestRestTemplate;
    import org.springframework.boot.web.server.LocalServerPort;
    import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
    import org.testng.Assert;
    import org.testng.annotations.Test;
    import xyz.srclab.common.serialize.json.JsonSerials;
    import xyz.srclab.spring.boot.web.message.EnableHttpReqMessageResolving;

    import javax.annotation.Resource;

    @SpringBootTest(
        classes = Starter.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
    )
    @EnableHttpReqMessageResolving
    public class WebMessageSample extends AbstractTestNGSpringContextTests {

        private static final Logger logger = LoggerFactory.getLogger(WebMessageSample.class);

        @LocalServerPort
        private int port;

        @Resource
        private TestRestTemplate restTemplate;

        @Test
        public void testMessage() {
            String result = restTemplate.getForObject(
                "http://localhost:" + port + "/test/message?req1=req1&req2=req2",
                String.class
            );
            logger.info("/test/exception?req1=req1&req2=req2: " + result);
            RespBody respBody = new RespBody();
            respBody.setResp1("req1");
            respBody.setResp2("req2");
            Assert.assertEquals(result, JsonSerials.toJsonString(respBody));
        }
    }

Kotlin Examples

    package sample.kotlin.xyz.srclab.spring.boot.web.message

    import org.slf4j.LoggerFactory
    import org.springframework.boot.autoconfigure.SpringBootApplication
    import org.springframework.boot.test.context.SpringBootTest
    import org.springframework.boot.test.web.client.TestRestTemplate
    import org.springframework.boot.web.server.LocalServerPort
    import org.springframework.test.context.testng.AbstractTestNGSpringContextTests
    import org.springframework.web.bind.annotation.RequestMapping
    import org.springframework.web.bind.annotation.RestController
    import org.springframework.web.servlet.ModelAndView
    import org.testng.Assert
    import org.testng.annotations.Test
    import xyz.srclab.common.serialize.json.toJsonString
    import xyz.srclab.spring.boot.web.message.EnableHttpReqMessageResolving
    import xyz.srclab.spring.boot.web.message.HttpReqMessage
    import xyz.srclab.spring.boot.web.message.HttpReqMessage.Companion.newHttpReqMessage
    import xyz.srclab.spring.boot.web.servlet.toHttpHeaders
    import javax.annotation.Resource
    import javax.servlet.http.HttpServletRequest

    @SpringBootTest(classes = [Starter::class], webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
    @EnableHttpReqMessageResolving
    open class WebMessageSample : AbstractTestNGSpringContextTests() {

        @LocalServerPort
        private val port = 0

        @Resource
        private val restTemplate: TestRestTemplate? = null

        @Test
        fun testMessage() {
            val result = restTemplate!!.getForObject(
                "http://localhost:$port/test/message?req1=req1&req2=req2",
                String::class.java
            )
            Companion.logger.info("/test/exception?req1=req1&req2=req2: $result")
            val respBody = RespBody()
            respBody.resp1 = "req1"
            respBody.resp2 = "req2"
            Assert.assertEquals(result, respBody.toJsonString())
        }

        companion object {
            private val logger = LoggerFactory.getLogger(WebMessageSample::class.java)
        }
    }

    @RequestMapping("test")
    @RestController
    open class MessageController {

        @RequestMapping("internal/message")
        fun testMessage(httpReqMessage: HttpReqMessage<ReqBody>): RespBody {
            val respBody = RespBody()
            respBody.resp1 = httpReqMessage.body!!.req1
            respBody.resp2 = httpReqMessage.body!!.req2
            return respBody
        }

        @RequestMapping("message")
        fun testMessage(reqBody: ReqBody?, servletRequest: HttpServletRequest): ModelAndView {
            val httpReqMessage = newHttpReqMessage<ReqBody>()
            httpReqMessage.metadata = servletRequest.toHttpHeaders()
            httpReqMessage.body = reqBody
            servletRequest.setAttribute("httpReqMessage", httpReqMessage)
            return ModelAndView("internal/message")
        }
    }

    open class ReqBody {
        var req1: String? = null
        var req2: String? = null
    }

    open class RespBody {
        var resp1: String? = null
        var resp2: String? = null
    }

    @SpringBootApplication
    open class Starter

#### Utilities

Web module provides some utilities:

-   WebExceptions: Provides exception tools for web;

-   WebServlets: Provides Servlet tools, such as fast build for
    ServletRequest, ServletInputStream.

## Contribution and Contact

-   <fredsuvn@163.com>

-   <https://github.com/srclab-projects/boat-spring-boot>

-   QQ group: 1037555759

## License

[Apache 2.0 license](https://www.apache.org/licenses/LICENSE-2.0.html)
