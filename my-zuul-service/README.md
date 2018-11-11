# my-zuul

## 1. 添加 maven 依赖

```xml
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-zuul</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
        </dependency>
```


## 2. 配置文件

bootstrap.yml:


```yml
spring:
  application:
    name: my-zuul
```

application.yml:

```yml
server:
  port: 8180

#### Spring
spring:
  profiles:
    active: @project.env@

#### Eureka :
eureka:
  instance:
    prefer-ip-address: true # 将自己的 IP 注册到 Eureka Server 上面。
  client:
    registryFetchIntervalSeconds: 5
    registerWithEureka: true
    fetchRegistry: true
    serviceUrl:
      defaultZone: http://127.0.0.1:8761/eureka/
```


### 启动类

```java

@SpringBootApplication
@EnableZuulProxy

```