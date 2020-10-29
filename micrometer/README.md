
## 集成到 Srping

### micrometer 



```xml
<!-- https://mvnrepository.com/artifact/io.micrometer/micrometer-registry-prometheus -->
<!-- micrometer 核心包，桥接 Prometheus -->
<dependency>
    <groupId>io.micrometer</groupId>
    <artifactId>micrometer-registry-prometheus</artifactId>
    <version>1.5.3</version>
</dependency>

<!-- https://mvnrepository.com/artifact/io.github.mweirauch/micrometer-jvm-extras -->
<!-- micrometer 获取 jvm 相关信息，用于展示在 Grafana 上 -->
<dependency>
    <groupId>io.github.mweirauch</groupId>
    <artifactId>micrometer-jvm-extras</artifactId>
    <version>0.2.0</version>
</dependency>


```


### 具体实现

1. MeterBinder 接口实现 bind 方法注册到 MeterRegistry 

2. 业务代码逻辑更新 Metrics。

3. 使用并且验证。


http://localhost:8081/actuator

http://localhost:8081/actuator/metrics

http://localhost:8081/actuator/metrics/charles.demo.gauge?tag=name:gauge1

## 邮件告警

下载 alertmanager 

- AlertManager 配置及启动

配置邮箱啥的。

- Prometheus 报警规则配置

- Prometheus.yml 文件增加 AlertManager 9093 端口及报警规则。


## Thanos 架构 （灭霸）


## PromQL 语句

需要投入时间去学习。

## Prometheus + K8s 生态

