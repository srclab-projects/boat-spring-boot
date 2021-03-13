# Boat Spring Boot: SrcLab核心基础Spring Boot库

![Boat Spring Boot](../logo.svg)

## 变量

* author: Sun Qian
* boat-spring-boot-version: 0.1.0

## 修订

|日期|修订版|作者|内容
|---|---|---|---|
|2020-3-5|1.0.0|{author}|旧项目
|2021-3-5|0.1.0|{author}|新项目

## Toc

* [简介](#introduction)
* [获取](#getting)
* [使用](#usage)
* [共享和联系方式](#contact)
* [License](#lecense)

## <a id="introduction"/>简介

Boat Spring Boot是一组基于[Boat][boat-url]的spring-boot项目集合. 它在[Boat][boat-url]的基础上, 提供了许多方便快捷的spring-boot接口, 函数和工具.

Boat Spring Boot包括:

* *boat-spring-boot-core*: 核心和基础的接口, 功能和工具, 比如spring bean操作;
* *boat-spring-boot-test*: 测试依赖管理, 用来在测试编译和运行范围下引入测试框架;
* *boat-spring-boot-bom*: Boat Spring Boot Bom.

根据Spring Boot官方推荐, 我们应当使用starter来导入Boat Spring Boot:

* boat-spring-boot-starter
* boat-spring-boot-starter-test
* boat-spring-boot-starter-bom

## <a id="getting"/>获取

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

## <a id="usage"/>使用

* [Core (boat-spring-boot-starter)](#usage-core)
  * [Bean](#usage-core-bean)
  * [Task](#usage-core-task)
  * [Schedule](#usage-core-schedule)
  * [Exception](#usage-core-exception)
* [Web (boat-spring-boot-web-starter)](#usage-web)
  * [Exception](#usage-web-exception)

### <a id="usage-core"/>Core (boat-spring-boot-starter)

#### <a id="usage-core-bean"/>Bean

Bean提供:

* BeanLifecyclePostProcessor: Spring Bean整个生命周期后置处理器;
* BeanRegistry: 动态bean注册.

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

Task提供:

* ThreadPoolProperties: 线程池属性;
* TaskExecutors: 快速创建TaskExecutor, 通常使用ThreadPoolProperties.

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

Schedule提供:

* ScheduledPoolProperties: 调度线程池属性;
* TaskSchedulers: 快速创建TaskScheduler, 通常使用ScheduledPoolProperties.

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

异常包提供:

* ExceptionStateHandler: 用户定义的bean, 将异常转为State;
* ExceptionStateService: 自动注入的全局异常处理服务, 使用ExceptionStateHandler;
* EnableExceptionStateService: 开启ExceptionStateService的注解.

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

Web异常提供:

* EnableWebExceptionStateService: Web全局异常处理, 使用core包的EnableExceptionStateService, 查阅它的资料就知道.

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

## <a id="contact"/>贡献和联系方式

* fredsuvn@163.com
* https://github.com/srclab-projects/boat-spring-boot
* QQ群: 1037555759

## <a id="license"/>License

[Apache 2.0 license][license]

[license]: https://www.apache.org/licenses/LICENSE-2.0.html

[boat-url]: https://github.com/srclab-projects/boat