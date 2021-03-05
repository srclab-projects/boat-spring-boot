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
  * [Core](#usage-core)
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

### <a id="usage-core"/>Spring Boot Core (boat-spring-boot-starter)

Core provides:

* SpringBeanPostProcessor: Global spring boot bean lifecycle post processor;
* SpringBeanRegistration: Dynamic bean registration.

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

## <a id="contact"/>Contribution and Contact

* fredsuvn@163.com
* https://github.com/srclab-projects
* QQ group: 1037555759

## <a id="lecense"/>License

[Apache 2.0 license][license]

[license]: https://www.apache.org/licenses/LICENSE-2.0.html

[boat-url]: https://github.com/srclab-projects/boat