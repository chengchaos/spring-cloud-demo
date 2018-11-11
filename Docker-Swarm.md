
参考： https://www.cnblogs.com/bigberg/p/8867326.html

## 一、简介

Docker 有个编排工具 docker-compose，可以将组成某个应用的多个 docker 容器编排在一起同时管理。同样在 Swarm 集群中，可以使用 Docker Stack 将一组相关联的服务进行编排管理。

Docker Stack 也是一个 yaml 文件，和一份 docker-compose.yml 文件差不多，指令也基本一致。但是与 compose 相比，其不支持 build, links 和 network_mode。 Docker stack 有一个新的指令 deploy。

> stack 不支持的指令：
> - build
> - cgroup_parent
> - container_name
> - devices
> - tmpfs
> - external_links
> - links
> - network_mode
> - restart
> - security_opt
> - stop_signal
> - sysctls
> - userns_mode

## 二、Deploy

Deploy 是用来指定 swarm 服务部署和运行时的相关配置，并且只有使用 docker stack deploy 部署 swarm 集群是才会生效。如果使用 docker-compose up 或者 docker-compose run 时，改选项会被忽略。要使用 deploy 选项，compose-file 中 version 的版本要在 3 或者 3+。

```yml
version: '3'
services:
  redis:
    image: redis:alpine
    deploy:
      replicas: 6
      update_config:
        parallelism: 2
        deplay: 10s
      restart_policy:
        condition: on-failure
```

### (1) ENDPOINT_MODE

指定 swarm 服务发现的模式

- endpoint_mode: vip

Docker 为 swarm 几圈服务分配一个虚拟IP（VIP），作为客户端到达集群服务的“前端”。Docker 在客户端和可用工作节点之间对服务的请求进行路由。而客户端不用知道有多少节点参与服务或者这些节点的 IP/PORT。（这是默认模式）

- endpoint_mode: dnsrr

DNS 轮训（DNSRR）服务发现不适用单个虚拟 IP 。Docker 为服务设置 DNS 条目，使得服务名称的 DNS 查询返回一个 IP 地址列表，并且客户端直接连接到其中的一个。如果您想使用自己的负载平衡器，或者混合 Windows 和 Linux 应用程序，则 DNS 轮训功能非常有用。


> 注： version 3.3+

```yml
version: "3.3"

services:
  wordpress:
    image: wordpress
    ports:
      - "8080:80"
    networks:
      - overlay
    deploy:
      mode: replicated
      replicas: 2
      endpoint_mode: vip
  mysql:
    image: mysql
    volumes:
      - db-data: /var/lib/mysql/data
    networks:
      - overlay
    deploy:
      mode: replicated
      replicas: 2
      endpoint_mode: dnsrr
volumes:
  db-data:
networks:
  overlay:

```

### (2) LABELS

指定服务的标签。这些标签仅在服务上设置，而不在服务的任何容器上设置。

```yml
version: "3"
services:
  web:
    image: web
    deploy: 
      labels:
        com.example.description: "This label will appear on the web service"
```

要改为在容器上设置标签，请在 deploy 之外使用标签键

```yml
version: "3"
services:
  web:
    image: web
    labels:
      com.example.description: "This label will appear on all containers for the web service"

```


### (3) MODE

全局（每个集群节点只有一个容器）或副本（指定容器的数量）。默认值为副本。

```yml
version: "3"
services:
  worker:
    image: dockersamples/examplevotingapp_work
    deploy:
      mode: global
```


### (4) PLACEMENT

指定约束和偏好设置

```yml
version: "3"
services:
  db:
    image: postgres
    deploy:
      placement:
        constraints:
          - node.role == manager
          - engine.labels.operatingsystem == ubuntu 14.04
        preferences:
          - spread: node.labels.zone
```


### (5) REPICAS

如果服务是副本模式（默认模式），可以指定该服务运行的容器数量。

```yml
version: "3"
services:
  worker:
    image: dockersamples/examplevotingapp_work
    networks:
      - frontend
      - backend
    deploy:
      mode: replicated
      replicas: 6
```

### (6) RESOURCES

资源限制配置

> 注意：这会替换版本3之前的Compose文件（cpu_shares，cpu_quota，cpuset，mem_limit，memswap_limit，mem_swappiness）
> 中的非群集模式的旧资源约束选项，如升级2.x版至3.x中所述。　

在下例中，redis 服务限制使用不超过 50M 的内存和 0.50 (50%) 的可用处理器时间（CPU），并且拥有 20M 的内存和 0.25 个 CPU 时间（总是可用）

```yml
version: "3"
services:
  redis:
    image: redis:alplie
    deploy:
      resources:
        limits:
          cpus: '0.50'
          memory: 50M
        reservations:
          cpus: '0.25'
          memory: 20M


```


### (7) RESTART_POLICY

配置在容器退出时是否并如何重启容器。取代 restart 指令。

- condition: none, on-failure 和 any （默认 any）
- delay： 在重启尝试之间等待多久（默认 0）
- max_attempts: 尝试重启的次数（默认一直重启，直到成功）
- window: 在确定一个重启是否成功前需要等待的窗口时间

```yml
version: "3"
services:
  redis:
    image: redis:alpine
    deploy:
      restart_policy:
        condition: on-failure
        delay: 5s
        max_attempts: 3
        window: 120s
```

### (8) UPDATE_CONFIG

配置服务如何升级

- parallelism： 同一时间升级的容器数量
- delay: 容器升级间隔时间
- failure_action: 升级失败后的动作（continue, rollback, pause。默认 pause）。
- monitor: 更新完成后确定成功的时间 （ns|us|ms|s|m|h)。（默认 0s）。
- max_failure_ratio: 更新期间允许的失败率。
- order: 更新期间的操作顺序。停止优先（旧任务在开始新任务之前停止）；先启动（首先启动新任务，并且正在运行的任务短暂重叠）；（默认停止优先）；注：只支持 v3.4 及更高版本。

```yml

version: '3.4'
services:
  vote:
    image: dockersamples/examplevotingapp_vote:before
    depends_on:
      - redis
    deploy:
      replicas: 2
      update_config:
        parallelism: 2
        deplay: 10s
        order: stop-first
```

### (9) depends_on

表示服务之间的依赖关系

```yml
version: "3"
services:
  web:
    build: .
    depends_on:
      - db
      - redis
  redis:
    image: redis
  db:
    image: postgres
```

### (10) DNS

自定义DNS服务器。可以是单个值或列表。　

```yml
dns: 8.8.8.8
dns:
  - 8.8.8.8
  - 9.9.9.9
```


### （11）dns_search　　

```yml
dns_search: example.com
dns_search:
  - dc1.example.com
  - dc2.example.com
```


### （12）environment　　

添加环境变量。您可以使用数组或字典。任何布尔值;真/假，是/否，需要用引号括起来以确保它们不被YML解析器转换为True或False。　

```yml
environment:
  RACK_ENV: development
  SHOW: 'true'
  SESSION_SECRET:
 
environment:
  - RACK_ENV=development
  - SHOW=true
  - SESSION_SECRET
```
　　
### （13）expose

开放容器的端口而不用在主机上暴露端口，它们只能被相关联的服务获取。只能指定内部端口。　

```yml
expose:
 - "3000"
 - "8000"
```