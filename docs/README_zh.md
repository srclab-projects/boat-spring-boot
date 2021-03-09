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

## <a id="contact"/>贡献和联系方式

* fredsuvn@163.com
* https://github.com/srclab-projects
* QQ群: 1037555759

## <a id="license"/>License

[Apache 2.0 license][license]

[license]: https://www.apache.org/licenses/LICENSE-2.0.html

[boat-url]: https://github.com/srclab-projects/boat