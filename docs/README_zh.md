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
  * [Core](#usage-core)
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

### <a id="usage-core"/>Spring Boot Core (boat-spring-boot-starter)

Core提供:

* SpringBeanPostProcessor: Spring的bean全局全生命周期后置处理器;
* SpringBeanRegistration: 动态bean注册.

#### Java Examples

```java

@Component
public class BeanRegistration implements SpringBeanRegistration {

  @NotNull
  @Override
  public Map<String, Object> registerSingletons() {
    Map<String, Object> map = new HashMap<>();
    map.put("bean1", "bean1");
    return map;
  }

  @NotNull
  @Override
  public Set<SpringSingletonGenerator> registerSingletonGenerators() {
    Set<SpringSingletonGenerator> set = new HashSet<>();
    set.add(new SpringSingletonGenerator() {
      @NotNull
      @Override
      public String name() {
        return "bean2";
      }

      @NotNull
      @Override
      public Object generate() {
        return "bean2";
      }
    });
    return set;
  }
}
```

#### Kotlin Examples

```kotlin
@Component
open class BeanRegistrationKt : SpringBeanRegistration {

  override fun registerSingletons(): Map<String, Any> {
    val map: MutableMap<String, Any> = HashMap()
    map["bean1Kt"] = "bean1Kt"
    return map
  }

  override fun registerSingletonGenerators(): Set<SpringSingletonGenerator> {
    val set: MutableSet<SpringSingletonGenerator> = HashSet()
    set.add(object : SpringSingletonGenerator {

      override val name: String = "bean2Kt"

      override fun generate(): Any {
        return "bean2Kt"
      }
    })
    return set
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