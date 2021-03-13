# Boat Spring Boot: SrcLab Core Libraries for Spring Boot

![Boat Spring Boot](../logo.svg)

## Variables

* author: Sun Qian
* boat-spring-boot-version: 0.1.0

## Revision

|Date|Revision|Author|Content|
|---|---|---|---|
|2020-3-5|1.0.0|{author}|Old project
|2021-3-5|0.1.0|{author}|New project

## Toc

* [Introduction](#introduction)
* [Getting](#getting)
* [Usage](#usage)
* [Contribution and Contact](#contact)
* [License](#lecense)

## <a id="introduction"/>Introduction

Boat Spring Boot is a set of spring-boot projects with [Boat][boat-url]. It provides many fast and convenient
interfaces, functions and utilities adapted to spring-boot with [Boat][boat-url].

Boat Spring Boot includes:

* *boat-spring-boot-core*: Core and basic interfaces, functions and utilities, such as spring bean operation;
* *boat-spring-boot-test*: Test dependencies management, to import common test libraries in test scope;
* *boat-spring-boot-bom*: Boat Spring Boot Bom.

Following the official recommendation of Spring Boot, we should use starter to import Boat Spring Boot:

* boat-spring-boot-starter
* boat-spring-boot-starter-test
* boat-spring-boot-starter-bom

## <a id="getting"/>Getting

### Gradle

```groovy
implementation("xyz.srclab.spring.boot:boat-spring-boot-starter:{boat-spring-boot-version}")
```

### Maven

```xml

<dependency>
  <groupId>xyz.srclab.spring.boot</groupId>
  <artifactId>boat-spring-boot-starter</artifactId>
  <version>{boat-spring-boot-version}</version>
</dependency>
```

### Source Code

https://github.com/srclab-projects/boat-spring-boot

## <a id="usage"/>Usage

* [Core (boat-spring-boot-starter)](#usage-core)
  * [Bean](#usage-core-bean)
  * [Task](#usage-core-task)
  * [Schedule](#usage-core-schedule)
  * [Exception](#usage-core-exception)
* [Web (boat-spring-boot-web-starter)](#usage-web)
  * [Exception](#usage-web-exception)

### <a id="usage-core"/>Core (boat-spring-boot-starter)

#### <a id="usage-core-bean"/>Bean

Bean provides:

* BeanLifecyclePostProcessor: Spring Bean lifecycle post processor;
* BeanRegistry: Dynamic bean registry.

##### Java Examples

```java

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
```

##### Kotlin Examples

```kotlin
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
```

#### <a id="usage-core-task"/>Task

Task provides:

* ThreadPoolProperties: Properties for thread pool;
* TaskExecutors: Help fast create TaskExecutor with ThreadPoolProperties.

##### Java Examples

```java

@Configuration
@EnableAsync
public class MyTaskExecutorConfiguration {

  @Bean
  public TaskExecutor taskExecutor() {
    ThreadPoolProperties poolProperties = new ThreadPoolProperties();
    poolProperties.setThreadNamePrefix("6666");
    return TaskExecutors.newTaskExecutor(poolProperties);
  }
}
```

##### Kotlin Examples

```kotlin
@Configuration
@EnableAsync
open class MyTaskExecutorConfigurationKt {

  @Bean
  open fun taskExecutor(): TaskExecutor {
    val poolProperties = ThreadPoolProperties()
    poolProperties.threadNamePrefix = "6666"
    return newTaskExecutor(poolProperties)
  }
}
```

#### <a id="usage-core-schedule"/>Schedule

Schedule provides:

* ScheduledPoolProperties: Properties for scheduled thread pool;
* TaskSchedulers: Help fast create TaskScheduler with ScheduledPoolProperties.

##### Java Examples

```java

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
```

##### Kotlin Examples

```kotlin
@Configuration
@EnableScheduling
open class MyTaskSchedulerConfiguration {

  @Bean
  open fun taskScheduler(): TaskScheduler {
    val poolProperties = ScheduledPoolProperties()
    poolProperties.threadNamePrefix = "6666"
    return newTaskScheduler(poolProperties)
  }
}
```

#### <a id="usage-core-exception"/>Exception

Exception provides:

* ExceptionStateHandler: Handler bean defined by user, to convert exception to State;
* ExceptionStateService: Auto-created global exception processing, use ExceptionStateHandler;
* EnableExceptionStateService: Annotation to enable ExceptionStateService function.

##### Java Examples

```java

@SpringBootTest(classes = Starter.class)
@EnableExceptionStateService
public class ExceptionStateServiceSample extends AbstractTestNGSpringContextTests {

  @Resource
  private ExceptionStateService exceptionStateService;

  @Test
  public void testExceptionStateService() {
    ExceptionStatus runtime = exceptionStateService.toState(new RuntimeException());
    Assert.assertEquals(runtime.code(), "102");
    ExceptionStatus throwable = exceptionStateService.toState(new Exception());
    Assert.assertEquals(throwable.code(), "101");
  }
}

@Component
public class RuntimeExceptionExceptionStateHandler implements ExceptionStateHandler<RuntimeException, ExceptionStatus> {

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

@Component
public class ThrowableExceptionStateHandler implements ExceptionStateHandler<Throwable, ExceptionStatus> {

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
```

##### Kotlin Examples

```kotlin
@SpringBootTest(classes = [Starter::class])
@EnableExceptionStateService
class ExceptionStateServiceSample : AbstractTestNGSpringContextTests() {

  @Resource
  private lateinit var exceptionStateService: ExceptionStateService

  @Test
  fun testExceptionStateService() {
    val runtime = exceptionStateService.toState<ExceptionStatus>(RuntimeException())
    Assert.assertEquals(runtime.code, "102")
    val throwable = exceptionStateService.toState<ExceptionStatus>(Exception())
    Assert.assertEquals(throwable.code, "101")
  }
}

@Component
open class RuntimeExceptionExceptionStateHandler :
  ExceptionStateHandler<RuntimeException, ExceptionStatus> {
  override val supportedExceptionType: Class<RuntimeException> = RuntimeException::class.java
  override fun handle(e: RuntimeException): ExceptionStatus {
    return ExceptionStatus.of("102")
  }
}

@Component
open class ThrowableExceptionStateHandler : ExceptionStateHandler<Throwable, ExceptionStatus> {
  override val supportedExceptionType: Class<Throwable> = Throwable::class.java
  override fun handle(e: Throwable): ExceptionStatus {
    return ExceptionStatus.of("101")
  }
}
```

### <a id="usage-web"/>Web (boat-spring-boot-web-starter)

#### <a id="usage-web-exception"/>Exception

Web exception provides:

* EnableWebExceptionStateService: Global web exception processing, use core's EnableExceptionStateService, see it.

##### Java Examples

```java

@SpringBootTest(
    classes = Starter.class,
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@EnableWebExceptionStateService
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
```

##### Kotlin Examples

```kotlin
@SpringBootTest(classes = [Starter::class], webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EnableWebExceptionStateService
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
```

## <a id="contact"/>Contribution and Contact

* fredsuvn@163.com
* https://github.com/srclab-projects/boat-spring-boot
* QQ group: 1037555759

## <a id="lecense"/>License

[Apache 2.0 license][license]

[license]: https://www.apache.org/licenses/LICENSE-2.0.html

[boat-url]: https://github.com/srclab-projects/boat