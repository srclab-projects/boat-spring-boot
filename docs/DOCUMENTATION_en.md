# <span class="image">![logo](../logo.svg)</span> Boat Spring Boot: SrcLab Core Libraries for Spring Boot and [Boat](https://github.com/srclab-projects/boat)

<span id="author" class="author">Sun Qian</span>  
<span id="email" class="email"><fredsuvn@163.com></span>  

Table of Contents

-   [Introduction](#_introduction)
-   [Getting](#_getting)
-   [Usage](#_usage)
    -   [Core (boat-spring-boot-core)](#_core_boat_spring_boot_core)
        -   [Core](#_core)
        -   [Bean](#_bean)
        -   [Message](#_message)
        -   [Task](#_task)
        -   [Schedule](#_schedule)
        -   [Exception](#_exception)
    -   [Web (boat-spring-boot-web)](#_web_boat_spring_boot_web)
        -   [Exception](#_exception_2)
        -   [Servlet](#_servlet)

## Introduction

Boat Spring Boot is spring-boot framework supporting with
[Boat](https://github.com/srclab-projects/boat). It provides many fast
and convenient interfaces and utilities adapted for spring-boot
framework.

Boat Spring Boot includes:

-   [Core (boat-spring-boot-core)](#_core_boat_spring_boot_core): Core
    and basic interfaces and utilities;

-   [Web (boat-spring-boot-web)](#_web_boat_spring_boot_web): Web
    interfaces and utilities;

-   `boat-spring-boot-test`: Testing libs dependencies management
    project;

-   `boat-spring-boot-bom`: BOM (gradle platform) project;

To import those modules, use their `starters`:

-   `boat-spring-boot-starter`

-   `boat-spring-boot-web-starter`

-   `boat-spring-boot-starter-test`

-   `boat-spring-boot-starter-bom`

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

### Core (boat-spring-boot-core)

#### Core

Core package provides core interfaces and utilities:

-   `KeyString`: Represents a key string may be encoded and encrypted,
    mainly used for non-plain text configure;

-   `StartGreeting`: Interface to define whether and how to show the
    greeting message when started;

-   `GreetingProperties`: Properties for `StartGreeting`;

Java Examples

    package sample.java.xyz.srclab.spring.boot.core;

    import org.slf4j.Logger;
    import org.slf4j.LoggerFactory;
    import org.springframework.boot.test.context.SpringBootTest;
    import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
    import org.testng.Assert;
    import org.testng.annotations.Test;
    import xyz.srclab.common.codec.aes.AesKeys;
    import xyz.srclab.spring.boot.autoconfigure.BoatAutoConfiguration;

    import javax.annotation.Resource;
    import javax.crypto.SecretKey;

    @SpringBootTest(classes = {
        BoatAutoConfiguration.class,
        TestKeyString.class
    })
    public class KeyStringSample extends AbstractTestNGSpringContextTests {

        private static final Logger log = LoggerFactory.getLogger(KeyStringSample.class);

        @Resource
        private TestKeyString testProperties;

        @Test
        public void testEncodeString() {
            log.info("encodeString: {}", testProperties.keyString);
            SecretKey key = AesKeys.newKey("123");
            Assert.assertEquals(testProperties.getKeyString().decodeString(key), "some password");

            log.info("testProperties.getEncodeString(): {}", testProperties.getKeyString());
            Assert.assertEquals(testProperties.getKeyString().decodeString(key), "some password");
        }
    }

    package sample.java.xyz.srclab.spring.boot.core;

    import org.springframework.beans.factory.annotation.Value;
    import xyz.srclab.spring.boot.core.KeyString;

    public class TestKeyString {

        @Value("AES,BASE64:rliqBhMdiKQDcH8lqNZdIg==")
        KeyString keyString;

        public KeyString getKeyString() {
            return keyString;
        }

        public void setKeyString(KeyString keyString) {
            this.keyString = keyString;
        }
    }

    package sample.java.xyz.srclab.spring.boot.core;

    import org.slf4j.Logger;
    import org.slf4j.LoggerFactory;
    import org.springframework.boot.test.context.SpringBootTest;
    import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
    import org.testng.annotations.Test;
    import xyz.srclab.spring.boot.autoconfigure.BoatAutoConfiguration;
    import xyz.srclab.spring.boot.core.StartGreeting;

    @SpringBootTest(classes = {
        BoatAutoConfiguration.class,
        GreetingSample.class
    })
    public class GreetingSample extends AbstractTestNGSpringContextTests implements StartGreeting {

        private static final Logger logger = LoggerFactory.getLogger(GreetingSample.class);

        @Test
        public void testAutoConfigure() {
        }

        @Override
        public void doGreeting() {
            logger.info(">>>>>>>>>>>>>>>>>> This is sample greeting!");
        }
    }

Kotlin Examples

    package sample.kotlin.xyz.srclab.spring.boot.core

    import org.slf4j.LoggerFactory
    import org.springframework.beans.factory.annotation.Value
    import org.springframework.boot.test.context.SpringBootTest
    import org.springframework.test.context.testng.AbstractTestNGSpringContextTests
    import org.testng.Assert
    import org.testng.annotations.Test
    import xyz.srclab.common.codec.aes.toAesKey
    import xyz.srclab.spring.boot.autoconfigure.BoatAutoConfiguration
    import xyz.srclab.spring.boot.core.KeyString
    import javax.annotation.Resource

    @SpringBootTest(
        classes = [
            BoatAutoConfiguration::class, TestKeyString::class]
    )
    open class KeyStringSample : AbstractTestNGSpringContextTests() {

        @Resource
        private lateinit var testProperties: TestKeyString

        @Test
        fun testEncodeString() {
            log.info("encodeString: {}", testProperties.keyString)
            val key = "123".toAesKey()
            Assert.assertEquals(testProperties.keyString.decodeString(key), "some password")
            log.info("testProperties.getEncodeString(): {}", testProperties.keyString)
            Assert.assertEquals(testProperties.keyString.decodeString(key), "some password")
        }

        companion object {
            private val log = LoggerFactory.getLogger(KeyStringSample::class.java)
        }
    }

    open class TestKeyString {
        @Value("AES,BASE64:rliqBhMdiKQDcH8lqNZdIg==")
        lateinit var keyString: KeyString
    }

    package sample.kotlin.xyz.srclab.spring.boot.core

    import org.slf4j.LoggerFactory
    import org.springframework.boot.test.context.SpringBootTest
    import org.springframework.test.context.testng.AbstractTestNGSpringContextTests
    import org.testng.annotations.Test
    import xyz.srclab.spring.boot.autoconfigure.BoatAutoConfiguration
    import xyz.srclab.spring.boot.core.StartGreeting

    @SpringBootTest(
        classes = [
            BoatAutoConfiguration::class, GreetingSample::class]
    )
    class GreetingSample : AbstractTestNGSpringContextTests(), StartGreeting {

        @Test
        fun testAutoConfigure() {
        }

        override fun doGreeting() {
            log.info(">>>>>>>>>>>>>>>>>> This is sample greeting!")
        }

        companion object {
            private val log = LoggerFactory.getLogger(GreetingSample::class.java)
        }
    }

#### Bean

Bean package provides:

-   `BeanProperties`: Properties for bean configure;

-   `BeanLifecyclePostProcessor`: Spring Bean lifecycle post processor;

-   `BeanRegistry`: Dynamic bean registry;

Java Examples

    package sample.java.xyz.srclab.spring.boot.bean;

    import org.slf4j.Logger;
    import org.slf4j.LoggerFactory;
    import org.springframework.boot.test.context.SpringBootTest;
    import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
    import org.testng.Assert;
    import org.testng.annotations.Test;
    import xyz.srclab.spring.boot.autoconfigure.BoatAutoConfiguration;

    import javax.annotation.Resource;
    import java.util.Arrays;

    @SpringBootTest(classes = {
        BoatAutoConfiguration.class,
        MyBean.class,
        MyBeanLifecyclePostProcessor.class,
        MyBeanRegistry.class,
    })
    public class BeanSample extends AbstractTestNGSpringContextTests {

        private static final Logger logger = LoggerFactory.getLogger(BeanSample.class);

        @Resource
        private MyBeanLifecyclePostProcessor myBeanLifecyclePostProcessor;

        @Resource
        private String bean1;

        @Resource
        private String bean2;

        @Resource
        private MyBean myBean;

        @Test
        public void testBeanPostProcessor() {
            logger.info("Bean processing sequence: {}", myBeanLifecyclePostProcessor.getSequence());
            Assert.assertEquals(
                myBeanLifecyclePostProcessor.getSequence(),
                Arrays.asList(
                    "postProcessBeanDefinitionRegistry",
                    "postProcessBeanFactory",
                    "postProcessBeforeInstantiation",
                    "postProcessAfterInstantiation",
                    "postProcessProperties",
                    "postProcessBeforeInitialization",
                    "postProcessAfterInitialization"
                )
            );
        }

        @Test
        public void testBeanManager() {
            logger.info("bean1: {}", bean1);
            Assert.assertEquals(bean1, "bean1");
            logger.info("bean2: {}", bean2);
            Assert.assertEquals(bean2, "bean2");
            logger.info("myBean: {}", myBean.getBeanString());
            Assert.assertEquals(myBean.getBeanString(), bean1 + bean2);
        }
    }

    package sample.java.xyz.srclab.spring.boot.bean;

    import org.jetbrains.annotations.NotNull;
    import org.springframework.beans.BeansException;
    import org.springframework.beans.PropertyValues;
    import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
    import org.springframework.beans.factory.support.BeanDefinitionRegistry;
    import xyz.srclab.spring.boot.bean.BeanLifecyclePostProcessor;

    import java.util.LinkedList;
    import java.util.List;

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

    package sample.java.xyz.srclab.spring.boot.bean;

    import org.springframework.context.annotation.DependsOn;

    import javax.annotation.Resource;

    @DependsOn("myBeanRegistry")
    public class MyBean {

        @Resource
        private String bean1;

        @Resource
        private String bean2;

        public String getBeanString() {
            return bean1 + bean2;
        }
    }

    package sample.java.xyz.srclab.spring.boot.bean;

    import org.jetbrains.annotations.NotNull;
    import xyz.srclab.spring.boot.bean.BeanProperties;
    import xyz.srclab.spring.boot.bean.BeanRegistry;

    import java.util.HashMap;
    import java.util.HashSet;
    import java.util.Map;
    import java.util.Set;

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
    import xyz.srclab.spring.boot.bean.BeanLifecyclePostProcessor;

    import java.util.LinkedList;
    import java.util.List;

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

    import org.slf4j.LoggerFactory
    import org.springframework.boot.test.context.SpringBootTest
    import org.springframework.context.annotation.DependsOn
    import org.springframework.test.context.testng.AbstractTestNGSpringContextTests
    import org.testng.Assert
    import org.testng.annotations.Test
    import xyz.srclab.spring.boot.autoconfigure.BoatAutoConfiguration
    import xyz.srclab.spring.boot.bean.BeanProperties
    import xyz.srclab.spring.boot.bean.BeanRegistry
    import javax.annotation.Resource

    @SpringBootTest(
        classes = [
            BoatAutoConfiguration::class, MyBean::class, MyBeanLifecyclePostProcessor::class, MyBeanRegistry::class]
    )
    open class BeanSample : AbstractTestNGSpringContextTests() {

        @Resource
        private lateinit var myBeanLifecyclePostProcessor: MyBeanLifecyclePostProcessor

        @Resource
        private lateinit var bean1: String

        @Resource
        private lateinit var bean2: String

        @Resource
        private lateinit var myBean: MyBean

        @Test
        fun testBeanPostProcessor() {
            log.info("Bean processing sequence: {}", myBeanLifecyclePostProcessor.sequence)
            Assert.assertEquals(
                myBeanLifecyclePostProcessor.sequence,
                listOf(
                    "postProcessBeanDefinitionRegistry",
                    "postProcessBeanFactory",
                    "postProcessBeforeInstantiation",
                    "postProcessAfterInstantiation",
                    "postProcessProperties",
                    "postProcessBeforeInitialization",
                    "postProcessAfterInitialization"
                )
            )
        }

        @Test
        fun testBeanManager() {
            log.info("bean1: {}", bean1)
            Assert.assertEquals(bean1, "bean1")
            log.info("bean2: {}", bean2)
            Assert.assertEquals(bean2, "bean2")
            log.info("myBean: {}", myBean.beanString)
            Assert.assertEquals(myBean.beanString, bean1 + bean2)
        }

        companion object {
            private val log = LoggerFactory.getLogger(BeanSample::class.java)
        }
    }

    @DependsOn("myBeanRegistry")
    open class MyBean {

        @Resource
        private lateinit var bean1: String

        @Resource
        private lateinit var bean2: String

        val beanString: String
            get() = bean1 + bean2
    }

    open class MyBeanRegistry : BeanRegistry() {

        override val registeredSingletons: Map<String, Any> = run {
            val result: MutableMap<String, Any> = HashMap()
            result["bean1"] = "bean1"
            result["bean2"] = "bean2"
            result
        }

        override val registeredBeans: Set<BeanProperties> = run {
            val result: MutableSet<BeanProperties> = HashSet()
            val beanProperties = BeanProperties()
            beanProperties.name = "myBean"
            beanProperties.className = MyBean::class.java.name
            result.add(beanProperties)
            result
        }
    }

    package sample.kotlin.xyz.srclab.spring.boot.bean

    import org.springframework.beans.BeansException
    import org.springframework.beans.PropertyValues
    import org.springframework.beans.factory.config.ConfigurableListableBeanFactory
    import org.springframework.beans.factory.support.BeanDefinitionRegistry
    import xyz.srclab.spring.boot.bean.BeanLifecyclePostProcessor
    import java.util.*

    open class MyBeanLifecyclePostProcessor : BeanLifecyclePostProcessor {

        val sequence: MutableList<String> = LinkedList()
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

        @Throws(BeansException::class)
        override fun postProcessBeforeInstantiation(beanClass: Class<*>, beanName: String): Any? {
            if (!isPostProcessBeforeInstantiation) {
                sequence.add("postProcessBeforeInstantiation")
                isPostProcessBeforeInstantiation = true
            }
            return null
        }

        @Throws(BeansException::class)
        override fun postProcessAfterInstantiation(bean: Any, beanName: String): Boolean {
            if (!isPostProcessAfterInstantiation) {
                sequence.add("postProcessAfterInstantiation")
                isPostProcessAfterInstantiation = true
            }
            return true
        }

        @Throws(BeansException::class)
        override fun postProcessBeforeInitialization(bean: Any, beanName: String): Any? {
            if (!isPostProcessBeforeInitialization) {
                sequence.add("postProcessBeforeInitialization")
                isPostProcessBeforeInitialization = true
            }
            return bean
        }

        @Throws(BeansException::class)
        override fun postProcessAfterInitialization(bean: Any, beanName: String): Any? {
            if (!isPostProcessAfterInitialization) {
                sequence.add("postProcessAfterInitialization")
                isPostProcessAfterInitialization = true
            }
            return null
        }

        @Throws(BeansException::class)
        override fun postProcessProperties(pvs: PropertyValues, bean: Any, beanName: String): PropertyValues? {
            if (!isPostProcessProperties) {
                sequence.add("postProcessProperties")
                isPostProcessProperties = true
            }
            return null
        }
    }

#### Message

Message package provides:

-   `ReqMessage`: Convenient request message definition;

-   `RespMessage`: Convenient response message definition;

#### Task

Task package provides:

-   `TaskPoolProperties`: Properties for task thread pool;

-   `TaskExecutors`: Help fast create `TaskExecutor` with
    `ThreadPoolProperties`;

-   `TaskDelegate`: Task execution delegate;

Java Examples

    package sample.java.xyz.srclab.spring.boot.task;

    import org.springframework.boot.test.context.SpringBootTest;
    import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
    import org.testng.annotations.Test;
    import xyz.srclab.common.lang.Current;
    import xyz.srclab.spring.boot.autoconfigure.BoatAutoConfiguration;

    import javax.annotation.Resource;

    @SpringBootTest(classes = {
        BoatAutoConfiguration.class,
        TaskExecutorConfiguration.class,
        AsyncService.class,
    })
    public class TaskSample extends AbstractTestNGSpringContextTests {

        @Resource
        private AsyncService asyncService;

        @Test
        public void testTask() {
            asyncService.testAsync();
            Current.sleep(1000);
        }
    }

    package sample.java.xyz.srclab.spring.boot.task;

    import org.slf4j.Logger;
    import org.slf4j.LoggerFactory;
    import org.slf4j.MDC;
    import org.springframework.scheduling.annotation.Async;
    import org.testng.Assert;
    import xyz.srclab.common.lang.Current;

    public class AsyncService {

        private static final Logger logger = LoggerFactory.getLogger(AsyncService.class);

        @Async
        public void testAsync() {
            logger.info(
                "Thread: {}",
                Current.thread().getName()
            );
            Assert.assertTrue(Current.thread().getName().startsWith("6666"));
            Assert.assertEquals(MDC.get("123"), "123");
        }
    }

    package sample.java.xyz.srclab.spring.boot.task;

    import org.slf4j.Logger;
    import org.slf4j.LoggerFactory;
    import org.slf4j.MDC;
    import org.springframework.context.annotation.Bean;
    import org.springframework.context.annotation.Configuration;
    import org.springframework.core.task.TaskExecutor;
    import org.springframework.scheduling.annotation.EnableAsync;
    import xyz.srclab.spring.boot.task.TaskDelegate;
    import xyz.srclab.spring.boot.task.TaskExecutors;
    import xyz.srclab.spring.boot.task.TaskPoolProperties;

    @Configuration
    @EnableAsync
    public class TaskExecutorConfiguration {

        private static final Logger logger = LoggerFactory.getLogger(TaskExecutorConfiguration.class);

        @Bean
        public TaskExecutor taskExecutor() {
            TaskPoolProperties poolProperties = new TaskPoolProperties();
            poolProperties.setThreadNamePrefix("6666");
            return TaskExecutors.newTaskExecutor(poolProperties, (TaskDelegate) (executor, task) -> {
                long l1 = Thread.currentThread().getId();
                MDC.put("123", "123");
                TaskExecutors.executeWithMdc(executor, () -> {
                    long l2 = Thread.currentThread().getId();
                    logger.info("thread l1: {}, thread l2: {}", l1, l2);
                    task.run();
                });
            });
        }
    }

Kotlin Examples

    package sample.kotlin.xyz.srclab.spring.boot.task

    import org.slf4j.LoggerFactory
    import org.slf4j.MDC
    import org.springframework.boot.test.context.SpringBootTest
    import org.springframework.context.annotation.Bean
    import org.springframework.context.annotation.Configuration
    import org.springframework.core.task.TaskExecutor
    import org.springframework.scheduling.annotation.Async
    import org.springframework.scheduling.annotation.EnableAsync
    import org.springframework.test.context.testng.AbstractTestNGSpringContextTests
    import org.testng.Assert
    import org.testng.annotations.Test
    import xyz.srclab.common.lang.Current.sleep
    import xyz.srclab.common.lang.Current.thread
    import xyz.srclab.spring.boot.autoconfigure.BoatAutoConfiguration
    import xyz.srclab.spring.boot.task.TaskDelegate
    import xyz.srclab.spring.boot.task.TaskPoolProperties
    import xyz.srclab.spring.boot.task.executeWithMdc
    import xyz.srclab.spring.boot.task.toTaskExecutor
    import java.util.concurrent.Executor
    import javax.annotation.Resource

    @SpringBootTest(
        classes = [
            BoatAutoConfiguration::class, TaskExecutorConfiguration::class, AsyncService::class]
    )
    open class TaskSample : AbstractTestNGSpringContextTests() {
        @Resource
        private lateinit var asyncService: AsyncService

        @Test
        fun testTask() {
            asyncService.testAsync()
            sleep(1000)
        }
    }

    open class AsyncService {
        @Async
        open fun testAsync() {
            logger.info(
                "Thread: {}",
                thread.name
            )
            Assert.assertTrue(thread.name.startsWith("6666"))
            Assert.assertEquals(MDC.get("123"), "123")
        }

        companion object {
            private val logger = LoggerFactory.getLogger(AsyncService::class.java)
        }
    }

    @Configuration
    @EnableAsync
    open class TaskExecutorConfiguration {
        @Bean
        open fun taskExecutor(): TaskExecutor {
            val poolProperties = TaskPoolProperties()
            poolProperties.threadNamePrefix = "6666"
            return poolProperties.toTaskExecutor(object : TaskDelegate {
                override fun execute(executor: Executor, task: Runnable) {
                    val l1 = Thread.currentThread().id
                    MDC.put("123", "123")
                    executeWithMdc(executor, {
                        val l2 = Thread.currentThread().id
                        logger.info("thread l1: {}, thread l2: {}", l1, l2)
                        task.run()
                    })
                }
            })
        }

        companion object {
            private val logger = LoggerFactory.getLogger(TaskExecutorConfiguration::class.java)
        }
    }

#### Schedule

Schedule package provides:

-   `ScheduledPoolProperties`: Properties for scheduled thread pool;

-   `TaskSchedulers`: Help fast create `TaskScheduler` with
    `ScheduledPoolProperties`;

Java Examples

    package sample.java.xyz.srclab.spring.boot.schedule;

    import org.springframework.boot.test.context.SpringBootTest;
    import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
    import org.testng.annotations.Test;
    import xyz.srclab.common.lang.Current;
    import xyz.srclab.spring.boot.autoconfigure.BoatAutoConfiguration;

    @SpringBootTest(classes = {
        BoatAutoConfiguration.class,
        TaskSchedulerConfiguration.class,
        ScheduleService.class,
    })
    public class ScheduleSample extends AbstractTestNGSpringContextTests {

        @Test
        public void testSchedule() {
            Current.sleep(2000);
        }
    }

    package sample.java.xyz.srclab.spring.boot.schedule;

    import org.slf4j.Logger;
    import org.slf4j.LoggerFactory;
    import org.springframework.scheduling.annotation.Scheduled;
    import org.testng.Assert;
    import xyz.srclab.common.lang.Current;

    public class ScheduleService {

        private static final Logger logger = LoggerFactory.getLogger(ScheduleService.class);

        @Scheduled(cron = "* * * * * *")
        public void testSchedule() {
            logger.info("Thread: {}", Current.thread().getName());
            Assert.assertTrue(Current.thread().getName().startsWith("6666"));
        }
    }

    package sample.java.xyz.srclab.spring.boot.schedule;

    import org.springframework.context.annotation.Bean;
    import org.springframework.context.annotation.Configuration;
    import org.springframework.scheduling.TaskScheduler;
    import org.springframework.scheduling.annotation.EnableScheduling;
    import xyz.srclab.spring.boot.schedule.ScheduledPoolProperties;
    import xyz.srclab.spring.boot.schedule.TaskSchedulers;

    @Configuration
    @EnableScheduling
    public class TaskSchedulerConfiguration {

        @Bean
        public TaskScheduler taskScheduler() {
            ScheduledPoolProperties poolProperties = new ScheduledPoolProperties();
            poolProperties.setThreadNamePrefix("6666");
            return TaskSchedulers.newTaskScheduler(poolProperties);
        }
    }

Kotlin Examples

    package sample.kotlin.xyz.srclab.spring.boot.schedule

    import org.slf4j.LoggerFactory
    import org.springframework.boot.test.context.SpringBootTest
    import org.springframework.context.annotation.Bean
    import org.springframework.context.annotation.Configuration
    import org.springframework.scheduling.TaskScheduler
    import org.springframework.scheduling.annotation.EnableScheduling
    import org.springframework.scheduling.annotation.Scheduled
    import org.springframework.test.context.testng.AbstractTestNGSpringContextTests
    import org.testng.Assert
    import org.testng.annotations.Test
    import xyz.srclab.common.lang.Current.sleep
    import xyz.srclab.common.lang.Current.thread
    import xyz.srclab.spring.boot.autoconfigure.BoatAutoConfiguration
    import xyz.srclab.spring.boot.schedule.ScheduledPoolProperties
    import xyz.srclab.spring.boot.schedule.toTaskScheduler

    @SpringBootTest(
        classes = [
            BoatAutoConfiguration::class, TaskSchedulerConfiguration::class, ScheduleService::class]
    )
    open class ScheduleSample : AbstractTestNGSpringContextTests() {
        @Test
        fun testSchedule() {
            sleep(2000)
        }
    }

    open class ScheduleService {
        @Scheduled(cron = "* * * * * *")
        fun testSchedule() {
            logger.info("Thread: {}", thread.name)
            Assert.assertTrue(thread.name.startsWith("6666"))
        }

        companion object {
            private val logger = LoggerFactory.getLogger(ScheduleService::class.java)
        }
    }

    @Configuration
    @EnableScheduling
    open class TaskSchedulerConfiguration {
        @Bean
        open fun taskScheduler(): TaskScheduler {
            val poolProperties = ScheduledPoolProperties()
            poolProperties.threadNamePrefix = "6666"
            return poolProperties.toTaskScheduler()
        }
    }

#### Exception

Exception package provides:

-   `EnableExceptionHandlingService`: Annotation to enable global
    exception handling service;

-   `ExceptionHandlingService`: Autowired global exception handling
    service, see its javadoc for more detail;

-   `ExceptionHandlingComponent`, `ExceptionHandlingMethod`: Annotations
    to provide exception handler for `ExceptionHandlingService`;

Java Examples

    package test.xyz.srclab.spring.boot.exception;

    import org.slf4j.Logger;
    import org.slf4j.LoggerFactory;
    import org.springframework.boot.test.context.SpringBootTest;
    import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
    import org.testng.Assert;
    import org.testng.annotations.Test;
    import xyz.srclab.common.exception.ExceptionStatus;
    import xyz.srclab.spring.boot.autoconfigure.BoatAutoConfiguration;
    import xyz.srclab.spring.boot.exception.EnableExceptionHandlingService;
    import xyz.srclab.spring.boot.exception.ExceptionHandlingService;

    import javax.annotation.Resource;

    @SpringBootTest(classes = {
        BoatAutoConfiguration.class,
        ExceptionHandler.class,
    })
    @EnableExceptionHandlingService
    public class ExceptionServiceTest extends AbstractTestNGSpringContextTests {

        public static final Logger logger = LoggerFactory.getLogger(ExceptionServiceTest.class);

        @Resource
        private ExceptionHandlingService exceptionHandlingService;

        @Test
        public void testExceptionHandlingService() {
            ExceptionStatus runtime = exceptionHandlingService.handle(new RuntimeException());
            logger.info("runtime: {}", runtime);
            Assert.assertEquals(runtime.code(), "102");
            ExceptionStatus throwable = exceptionHandlingService.handle(new Exception());
            logger.info("throwable: {}", throwable);
            Assert.assertEquals(throwable.code(), "101");
        }
    }

    package test.xyz.srclab.spring.boot.exception;

    import org.jetbrains.annotations.NotNull;
    import xyz.srclab.common.exception.ExceptionStatus;
    import xyz.srclab.spring.boot.exception.ExceptionHandlingComponent;
    import xyz.srclab.spring.boot.exception.ExceptionHandlingMethod;

    @ExceptionHandlingComponent
    public class ExceptionHandler {

        @ExceptionHandlingMethod
        public ExceptionStatus handle(@NotNull RuntimeException exception) {
            return ExceptionStatus.of("102");
        }

        @ExceptionHandlingMethod
        public ExceptionStatus handle(@NotNull Throwable throwable) {
            return ExceptionStatus.of("101");
        }
    }

Kotlin Examples

    package sample.kotlin.xyz.srclab.spring.boot.exception

    import org.slf4j.LoggerFactory
    import org.springframework.boot.test.context.SpringBootTest
    import org.springframework.test.context.testng.AbstractTestNGSpringContextTests
    import org.testng.Assert
    import org.testng.annotations.Test
    import xyz.srclab.common.exception.ExceptionStatus
    import xyz.srclab.spring.boot.autoconfigure.BoatAutoConfiguration
    import xyz.srclab.spring.boot.exception.EnableExceptionHandlingService
    import xyz.srclab.spring.boot.exception.ExceptionHandlingComponent
    import xyz.srclab.spring.boot.exception.ExceptionHandlingMethod
    import xyz.srclab.spring.boot.exception.ExceptionHandlingService
    import javax.annotation.Resource

    @SpringBootTest(
        classes = [
            BoatAutoConfiguration::class, ExceptionHandler::class]
    )
    @EnableExceptionHandlingService
    class ExceptionServiceSample : AbstractTestNGSpringContextTests() {

        @Resource
        private lateinit var exceptionHandlingService: ExceptionHandlingService

        @Test
        fun testExceptionStateService() {
            val runtime = exceptionHandlingService.handle<ExceptionStatus>(RuntimeException())
            log.info("runtime: {}", runtime)
            Assert.assertEquals(runtime.code, "102")
            val throwable = exceptionHandlingService.handle<ExceptionStatus>(Exception())
            log.info("throwable: {}", throwable)
            Assert.assertEquals(throwable.code, "101")
        }

        companion object {
            private val log = LoggerFactory.getLogger(ExceptionServiceSample::class.java)
        }
    }

    @ExceptionHandlingComponent
    class ExceptionHandler {

        @ExceptionHandlingMethod
        fun handle(exception: RuntimeException): ExceptionStatus {
            return ExceptionStatus.of("102")
        }

        @ExceptionHandlingMethod
        fun handle(throwable: Throwable): ExceptionStatus {
            return ExceptionStatus.of("101")
        }
    }

### Web (boat-spring-boot-web)

#### Exception

Web exception package provides:

-   `EnableWebExceptionHandling`: Annotation to enable global web
    exception service;

-   `WebStatusException`: Exception for web;

-   `ExceptionResponseBody`: Exception response body for web;

Java Examples

    package sample.java.xyz.srclab.spring.boot.web.exception;

    import org.slf4j.Logger;
    import org.slf4j.LoggerFactory;
    import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
    import org.springframework.boot.test.context.SpringBootTest;
    import org.springframework.boot.test.web.client.TestRestTemplate;
    import org.springframework.boot.web.server.LocalServerPort;
    import org.springframework.http.HttpStatus;
    import org.springframework.http.ResponseEntity;
    import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
    import org.testng.Assert;
    import org.testng.annotations.Test;
    import xyz.srclab.spring.boot.autoconfigure.BoatAutoConfiguration;
    import xyz.srclab.spring.boot.web.exception.EnableWebExceptionHandling;
    import xyz.srclab.spring.boot.web.exception.ExceptionResponseBody;

    import javax.annotation.Resource;
    import java.util.Objects;

    @SpringBootTest(
        classes = {
            BoatAutoConfiguration.class,
            ExceptionHandler.class,
            TestController.class,
        },
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
    )
    @EnableWebExceptionHandling
    @EnableAutoConfiguration
    public class WebExceptionSample extends AbstractTestNGSpringContextTests {

        private static final Logger logger = LoggerFactory.getLogger(WebExceptionSample.class);

        @LocalServerPort
        private int port;

        @Resource
        private TestRestTemplate restTemplate;

        @Test
        public void testIllegalException() {
            ResponseEntity<ExceptionResponseBody> result = restTemplate.getForEntity(
                "http://localhost:" + port + "/test/illegalState",
                ExceptionResponseBody.class
            );
            logger.info("/test/illegalState: {}", result);
            Assert.assertEquals(result.getStatusCode(), HttpStatus.INTERNAL_SERVER_ERROR);
            Assert.assertEquals(Objects.requireNonNull(result.getBody()).getCode(), "101");
        }

        @Test
        public void testRuntimeException() {
            ResponseEntity<ExceptionResponseBody> result = restTemplate.getForEntity(
                "http://localhost:" + port + "/test/runtimeException",
                ExceptionResponseBody.class
            );
            logger.info("/test/runtimeException: {}", result);
            Assert.assertEquals(result.getStatusCode(), HttpStatus.BAD_REQUEST);
            Assert.assertEquals(Objects.requireNonNull(result.getBody()).getCode(), "102");
        }

        @Test
        public void testException() {
            ResponseEntity<String> result3 = restTemplate.getForEntity(
                "http://localhost:" + port + "/test/exception",
                String.class
            );
            logger.info("/test/exception: {}", result3);
            Assert.assertEquals(result3.getStatusCode(), HttpStatus.OK);
            Assert.assertEquals(result3.getBody(), "103");
        }
    }

    package sample.java.xyz.srclab.spring.boot.web.exception;

    import org.springframework.web.bind.annotation.RequestMapping;
    import org.springframework.web.bind.annotation.RestController;

    import java.util.Map;

    @RequestMapping("test")
    @RestController
    public class TestController {

        @RequestMapping("illegalState")
        public Map<Object, Object> testIllegalState() {
            throw new IllegalStateException();
        }

        @RequestMapping("runtimeException")
        public Map<Object, Object> testRuntimeException() {
            throw new RuntimeException();
        }

        @RequestMapping("exception")
        public Map<Object, Object> testException() throws Exception {
            throw new Exception();
        }
    }

    package sample.java.xyz.srclab.spring.boot.web.exception;

    import org.jetbrains.annotations.NotNull;
    import org.springframework.http.HttpStatus;
    import org.springframework.http.ResponseEntity;
    import xyz.srclab.common.exception.ExceptionStatus;
    import xyz.srclab.spring.boot.exception.ExceptionHandlingComponent;
    import xyz.srclab.spring.boot.exception.ExceptionHandlingMethod;
    import xyz.srclab.spring.boot.web.exception.WebStatusException;

    @ExceptionHandlingComponent
    public class ExceptionHandler {

        @ExceptionHandlingMethod
        public Object handle(@NotNull IllegalStateException illegalStateException) {
            return ExceptionStatus.of("101");
        }

        @ExceptionHandlingMethod
        public Object handle(@NotNull RuntimeException runtimeException) {
            return new WebStatusException("102", "desc", null, HttpStatus.BAD_REQUEST);
        }

        @ExceptionHandlingMethod
        public Object handle(@NotNull Exception exception) {
            return new ResponseEntity<>("103", HttpStatus.OK);
        }
    }

Kotlin Examples

    package sample.kotlin.xyz.srclab.spring.boot.web.exception

    import org.slf4j.LoggerFactory
    import org.springframework.boot.autoconfigure.EnableAutoConfiguration
    import org.springframework.boot.test.context.SpringBootTest
    import org.springframework.boot.test.web.client.TestRestTemplate
    import org.springframework.boot.web.server.LocalServerPort
    import org.springframework.http.HttpStatus
    import org.springframework.http.ResponseEntity
    import org.springframework.test.context.testng.AbstractTestNGSpringContextTests
    import org.springframework.web.bind.annotation.RequestMapping
    import org.springframework.web.bind.annotation.RestController
    import org.testng.Assert
    import org.testng.annotations.Test
    import xyz.srclab.common.exception.ExceptionStatus
    import xyz.srclab.spring.boot.autoconfigure.BoatAutoConfiguration
    import xyz.srclab.spring.boot.exception.ExceptionHandlingComponent
    import xyz.srclab.spring.boot.exception.ExceptionHandlingMethod
    import xyz.srclab.spring.boot.web.exception.EnableWebExceptionHandling
    import xyz.srclab.spring.boot.web.exception.ExceptionResponseBody
    import xyz.srclab.spring.boot.web.exception.WebStatusException
    import java.util.*
    import javax.annotation.Resource

    @SpringBootTest(
        classes = [BoatAutoConfiguration::class, ExceptionHandler::class, TestController::class],
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
    )
    @EnableWebExceptionHandling
    @EnableAutoConfiguration
    class WebExceptionSample : AbstractTestNGSpringContextTests() {

        @LocalServerPort
        private var port = 0

        @Resource
        private lateinit var restTemplate: TestRestTemplate

        @Test
        fun testIllegalException() {
            val result = restTemplate.getForEntity(
                "http://localhost:$port/test/illegalState",
                ExceptionResponseBody::class.java
            )
            log.info("/test/illegalState: {}", result)
            Assert.assertEquals(result.statusCode, HttpStatus.INTERNAL_SERVER_ERROR)
            Assert.assertEquals(Objects.requireNonNull(result.body).code, "101")
        }

        @Test
        fun testRuntimeException() {
            val result = restTemplate.getForEntity(
                "http://localhost:$port/test/runtimeException",
                ExceptionResponseBody::class.java
            )
            log.info("/test/runtimeException: {}", result)
            Assert.assertEquals(result.statusCode, HttpStatus.BAD_REQUEST)
            Assert.assertEquals(Objects.requireNonNull(result.body).code, "102")
        }

        @Test
        fun testException() {
            val result3 = restTemplate.getForEntity(
                "http://localhost:$port/test/exception",
                String::class.java
            )
            log.info("/test/exception: {}", result3)
            Assert.assertEquals(result3.statusCode, HttpStatus.OK)
            Assert.assertEquals(result3.body, "103")
        }

        companion object {
            private val log = LoggerFactory.getLogger(WebExceptionSample::class.java)
        }
    }

    @RequestMapping("test")
    @RestController
    class TestController {

        @RequestMapping("illegalState")
        fun testIllegalState(): Map<Any, Any> {
            throw IllegalStateException()
        }

        @RequestMapping("runtimeException")
        fun testRuntimeException(): Map<Any, Any> {
            throw RuntimeException()
        }

        @RequestMapping("exception")
        fun testException(): Map<Any, Any> {
            throw Exception()
        }
    }

    @ExceptionHandlingComponent
    class ExceptionHandler {

        @ExceptionHandlingMethod
        fun handle(illegalStateException: IllegalStateException): Any {
            return ExceptionStatus.of("101")
        }

        @ExceptionHandlingMethod
        fun handle(runtimeException: RuntimeException): Any {
            return WebStatusException("102", "desc", null, HttpStatus.BAD_REQUEST)
        }

        @ExceptionHandlingMethod
        fun handle(exception: Exception): Any {
            return ResponseEntity("103", HttpStatus.OK)
        }
    }

#### Servlet

Servlet package provides:

-   `WebServlets`: Provides Servlet tools, such as fast build for
    `ServletRequest`, `ServletInputStream`;

Java Examples

    package sample.java.xyz.srclab.spring.boot.web.servlet;

    import org.slf4j.Logger;
    import org.slf4j.LoggerFactory;
    import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
    import org.springframework.boot.test.context.SpringBootTest;
    import org.springframework.boot.test.web.client.TestRestTemplate;
    import org.springframework.boot.web.server.LocalServerPort;
    import org.springframework.http.ResponseEntity;
    import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
    import org.testng.Assert;
    import org.testng.annotations.Test;
    import xyz.srclab.common.exception.ExceptionStatus;
    import xyz.srclab.spring.boot.web.exception.ExceptionResponseBody;

    import javax.annotation.Resource;
    import java.util.Objects;

    @SpringBootTest(
        classes = {
            TestController.class,
            TestFilter.class,
        },
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
    )
    @EnableAutoConfiguration
    public class ServletSample extends AbstractTestNGSpringContextTests {

        private static final Logger logger = LoggerFactory.getLogger(ServletSample.class);

        @LocalServerPort
        private int port;

        @Resource
        private TestRestTemplate restTemplate;

        @Test
        public void testServlet() {
            String result = restTemplate.postForObject(
                "http://localhost:" + port + "/test/servlet",
                "ppp1",
                String.class
            );
            logger.info("/test/servlet: " + result);
            Assert.assertEquals(result, "ppp1");
        }

        @Test
        public void testIndex() {
            String result = restTemplate.postForObject(
                "http://localhost:" + port + "/test/index",
                "ppp2",
                String.class
            );
            logger.info("/test/index: " + result);
            Assert.assertEquals(result, "encode: ppp2");
        }

        @Test
        public void testException() {
            ResponseEntity<ExceptionResponseBody> result = restTemplate.getForEntity(
                "http://localhost:" + port + "/test/exception",
                ExceptionResponseBody.class
            );
            logger.info("/test/exception: " + result);
            logger.info("/test/exception: " + restTemplate.getForObject(
                "http://localhost:" + port + "/test/exception",
                String.class
            ));
            Assert.assertEquals(Objects.requireNonNull(result.getBody()).getCode(), ExceptionStatus.INTERNAL.code());
        }
    }

    package sample.java.xyz.srclab.spring.boot.web.servlet;

    import org.springframework.web.bind.annotation.RequestMapping;
    import org.springframework.web.bind.annotation.RestController;
    import org.springframework.web.servlet.ModelAndView;

    import java.util.HashMap;
    import java.util.Map;

    @RequestMapping("test")
    @RestController
    public class TestController {

        @RequestMapping("servlet")
        public String testServlet(String p1) {
            return p1;
        }

        @RequestMapping("index")
        public ModelAndView testIndex(String p1) {
            Map<String, Object> model = new HashMap<>();
            model.put("pm", p1);
            return new ModelAndView("encode", model);
        }

        @RequestMapping("encode")
        public String testEncode(String pm) {
            return "encode: " + pm;
        }

        @RequestMapping("exception")
        public String testException() {
            throw new RuntimeException("hello");
        }
    }

    package sample.java.xyz.srclab.spring.boot.web.servlet;

    import org.apache.commons.io.IOUtils;
    import org.springframework.http.HttpHeaders;
    import org.springframework.http.HttpStatus;
    import org.springframework.http.ResponseEntity;
    import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
    import org.springframework.web.filter.OncePerRequestFilter;
    import xyz.srclab.spring.boot.web.exception.ExceptionResponseBody;
    import xyz.srclab.spring.boot.web.exception.WebExceptions;
    import xyz.srclab.spring.boot.web.servlet.WebServlets;

    import javax.annotation.Resource;
    import javax.servlet.FilterChain;
    import javax.servlet.ServletException;
    import javax.servlet.http.HttpServletRequest;
    import javax.servlet.http.HttpServletResponse;
    import java.io.IOException;
    import java.nio.charset.StandardCharsets;
    import java.util.Collections;
    import java.util.HashMap;
    import java.util.List;
    import java.util.Map;

    public class TestFilter extends OncePerRequestFilter {

        @Resource
        private MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter;

        @Override
        protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
        ) throws ServletException, IOException {
            String p1 = IOUtils.toString(request.getInputStream(), StandardCharsets.UTF_8);
            Map<String, List<String>> parameters = new HashMap<>();
            parameters.put("p1", Collections.singletonList(p1));
            HttpServletRequest newRequest = WebServlets.newPreparedHttpServletRequest(request, parameters);
            try {
                filterChain.doFilter(newRequest, response);
            } catch (Exception e) {
                HttpHeaders header = new HttpHeaders();
                header.set(HttpHeaders.CONTENT_TYPE, "application/json");
                ResponseEntity<ExceptionResponseBody> responseEntity = new ResponseEntity<>(
                    WebExceptions.toExceptionResponseBody(e),
                    header,
                    HttpStatus.INTERNAL_SERVER_ERROR
                );
                WebServlets.writeFromResponseEntity(response, responseEntity, (body, out) -> {
                    try {
                        mappingJackson2HttpMessageConverter.getObjectMapper().writeValue(out, body);
                    } catch (IOException ioException) {
                        throw new IllegalStateException((ioException));
                    }
                    return null;
                });
            }
        }
    }

Kotlin Examples

    package sample.kotlin.xyz.srclab.spring.boot.web.servlet

    import org.apache.commons.io.IOUtils
    import org.slf4j.LoggerFactory
    import org.springframework.boot.autoconfigure.EnableAutoConfiguration
    import org.springframework.boot.test.context.SpringBootTest
    import org.springframework.boot.test.web.client.TestRestTemplate
    import org.springframework.boot.web.server.LocalServerPort
    import org.springframework.http.HttpHeaders
    import org.springframework.http.HttpStatus
    import org.springframework.http.ResponseEntity
    import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
    import org.springframework.test.context.testng.AbstractTestNGSpringContextTests
    import org.springframework.web.bind.annotation.RequestMapping
    import org.springframework.web.bind.annotation.RestController
    import org.springframework.web.filter.OncePerRequestFilter
    import org.springframework.web.servlet.ModelAndView
    import org.testng.Assert
    import org.testng.annotations.Test
    import xyz.srclab.common.exception.ExceptionStatus
    import xyz.srclab.spring.boot.web.exception.ExceptionResponseBody
    import xyz.srclab.spring.boot.web.exception.toExceptionResponseBody
    import xyz.srclab.spring.boot.web.servlet.toPreparedHttpServletRequest
    import xyz.srclab.spring.boot.web.servlet.writeFromResponseEntity
    import java.io.IOException
    import java.nio.charset.StandardCharsets
    import java.util.*
    import javax.annotation.Resource
    import javax.servlet.FilterChain
    import javax.servlet.ServletException
    import javax.servlet.ServletOutputStream
    import javax.servlet.http.HttpServletRequest
    import javax.servlet.http.HttpServletResponse

    @SpringBootTest(
        classes = [TestController::class, TestFilter::class],
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
    )
    @EnableAutoConfiguration
    class ServletSample : AbstractTestNGSpringContextTests() {

        @LocalServerPort
        private var port = 0

        @Resource
        private lateinit var restTemplate: TestRestTemplate

        @Test
        fun testServlet() {
            val result = restTemplate!!.postForObject(
                "http://localhost:$port/test/servlet",
                "ppp1",
                String::class.java
            )
            log.info("/test/servlet: $result")
            Assert.assertEquals(result, "ppp1")
        }

        @Test
        fun testIndex() {
            val result = restTemplate!!.postForObject(
                "http://localhost:$port/test/index",
                "ppp2",
                String::class.java
            )
            log.info("/test/index: $result")
            Assert.assertEquals(result, "encode: ppp2")
        }

        @Test
        fun testException() {
            val result = restTemplate!!.getForEntity(
                "http://localhost:$port/test/exception",
                ExceptionResponseBody::class.java
            )
            log.info("/test/exception: $result")
            log.info(
                "/test/exception: " + restTemplate.getForObject(
                    "http://localhost:$port/test/exception",
                    String::class.java
                )
            )
            Assert.assertEquals(Objects.requireNonNull(result.body).code, ExceptionStatus.INTERNAL.code)
        }

        companion object {
            private val log = LoggerFactory.getLogger(ServletSample::class.java)
        }
    }

    @RequestMapping("test")
    @RestController
    class TestController {

        @RequestMapping("servlet")
        fun testServlet(p1: String): String {
            return p1
        }

        @RequestMapping("index")
        fun testIndex(p1: String?): ModelAndView {
            val model: MutableMap<String, Any?> = HashMap()
            model["pm"] = p1
            return ModelAndView("encode", model)
        }

        @RequestMapping("encode")
        fun testEncode(pm: String): String {
            return "encode: $pm"
        }

        @RequestMapping("exception")
        fun testException(): String {
            throw RuntimeException("hello")
        }
    }

    class TestFilter : OncePerRequestFilter() {

        @Resource
        private lateinit var mappingJackson2HttpMessageConverter: MappingJackson2HttpMessageConverter

        @Throws(ServletException::class, IOException::class)
        override fun doFilterInternal(
            request: HttpServletRequest,
            response: HttpServletResponse,
            filterChain: FilterChain
        ) {
            val p1 = IOUtils.toString(request.inputStream, StandardCharsets.UTF_8)
            val parameters: MutableMap<String, List<String>> = java.util.HashMap()
            parameters["p1"] = listOf(p1)
            val newRequest: HttpServletRequest = request.toPreparedHttpServletRequest(parameters)
            try {
                filterChain.doFilter(newRequest, response)
            } catch (e: Exception) {
                val header = HttpHeaders()
                header[HttpHeaders.CONTENT_TYPE] = "application/json"
                val responseEntity = ResponseEntity(
                    e.toExceptionResponseBody(),
                    header,
                    HttpStatus.INTERNAL_SERVER_ERROR
                )
                response.writeFromResponseEntity(responseEntity) { body: Any?, out: ServletOutputStream? ->
                    try {
                        mappingJackson2HttpMessageConverter.objectMapper.writeValue(out, body)
                    } catch (ioException: IOException) {
                        throw IllegalStateException(ioException)
                    }
                }
            }
        }
    }
