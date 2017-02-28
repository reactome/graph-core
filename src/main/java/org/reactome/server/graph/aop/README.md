<img src=https://cloud.githubusercontent.com/assets/6883670/22938783/bbef4474-f2d4-11e6-92a5-07c1a6964491.png width=220 height=100 />

Aspect Oriented Programming with Spring
===

:warning: Changes in this package have to be taken carefully, considering it is going to reflect in the whole application

### What is Aspect-Oriented Programming - AOP?

Spring AOP framework is used to modularize cross-cutting concerns in aspects. Simply, t’s just an interceptor to intercept some processes, for example, when a method is execute, Spring AOP can hijack the executing method, and add extra functionality before or after the method execution. The most used feature is logging.
However, in Reactome, we are taking benefit of this concept in order to implement our own Lazy-Loading mechanism. This functionality is also available in ORM frameworks, such as, Hibernate and to achieve this they are implementing AOP concepts. Sorting and Logging aspect are present as well.

Complete information of Spring AOP you'll find [here](https://docs.spring.io/spring/docs/current/spring-framework-reference/html/aop.html).

### What are the `@PointCut` in Reactome for LazyLoading ?

```java
execution(public java.util.Collection<org.reactome.server.graph.domain.model.DatabaseObject+>+ org.reactome.server.graph.domain.model.*.get*(..))
```
Intercepting ALL the getters in our Domain Model whose return type is `Collection<? extends DatabaseObject>`

```java
execution(public org.reactome.server.graph.domain.model.DatabaseObject+ org.reactome.server.graph.domain.model.*.get*(..))
```
Intercepting ALL the getters in our Domain Model whose return type is `DatabaseObject` or `instance of`

### How does it work ?

Mainly, we are loading DEPTH{2} data from the graph without any relationship previously loaded, only identifiers. Once intercepted, the code checks whether the AOP is enabled and whether the object has been loaded previously. Substantially using Java Reflection, the code is capable to identify all the information regarding the method that has been intercepted, for instance, return type (`Collection` or `DatabaseObject`) and `@Relationship` which contains important information for querying against the Graph. Up to this point, the data have been retrieve and the code invokes the setter and proceed.

### Setting the `@Bean` in the General Configuration

```java
@Bean
public LazyFetchAspect lazyFetchAspect() {
    return org.aspectj.lang.Aspects.aspectOf(LazyFetchAspect.class);
}
```

### Configuring the pom.xml

#### Maven Dependencies

```html
<!--Aspectj AOP -->
<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-aspects</artifactId>
    <version>4.3.0.RELEASE</version>
</dependency>
<dependency>
    <groupId>org.aspectj</groupId>
    <artifactId>aspectjrt</artifactId>
    <version>1.8.10</version>
</dependency>
```

```html
<!--
    Maven plugin is necessary to test AspectJ when running in command line.
    Running on IntelliJ will fire the ajc and this will work automatically.
-->
<plugin>
    <groupId>org.codehaus.mojo</groupId>
    <artifactId>aspectj-maven-plugin</artifactId>
    <version>1.4</version>
    <configuration>
        <source>1.8</source>
        <target>1.8</target>
    </configuration>
    <executions>
        <execution>
            <id>test-compile</id>
            <configuration>
                <weaveMainSourceFolder>true</weaveMainSourceFolder>
            </configuration>
            <goals>
                <goal>compile</goal>       <!-- use this goal to weave all your main classes -->
                <goal>test-compile</goal>  <!-- use this goal to weave all your test classes -->
            </goals>
        </execution>
    </executions>
    <dependencies>
        <dependency>
            <groupId>org.aspectj</groupId>
            <artifactId>aspectjrt</artifactId>
            <version>${aspectj.version}</version>
        </dependency>
        <dependency>
            <groupId>org.aspectj</groupId>
            <artifactId>aspectjtools</artifactId>
            <version>${aspectj.version}</version>
        </dependency>
    </dependencies>
</plugin>
```

#### What is the flag `enableAOP` ?

The AOP is enabled by default, but in certain projects like [Content Service](https://github.com/reactome-pwp/content-service.git) where we respond a serialised JSON, the `@PointCut` will be invoked every where, every time, endless times. Thus, given the requirements of the `Content Service` it makes sense that we disable this feature. However, in the [Data Content](https://github.com/reactome/data-content) we kept it enabled.


=

### SortingAspect ?

Intercepting ALL the getters in our Domain Model whose return type is `Collection<? extends DatabaseObject>` and sorting the list based on the `displayName`.

=

### LoggingAspect ?

Logging everything at the service level. The package `service` is being intercepted in order to measure the execution time.