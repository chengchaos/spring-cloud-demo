#

Swarm 是 Docker 社区唯一原生支持 Docker 集群的管理工具。


- 调度模块 filter
  - Constraints
  - Affinity 亲和性过滤器
  - Dependency 依赖过滤器
  - Health 健康状态
  - Ports filter 
- 调度策略 strategy
  - Binpack
  - Spread
  - Random 随机选择节点

### 服务发现

- Ingress (Overlay 网络)
- Ingress + link
- 自定义网络

#### Ingress 

一个服务在集群的所有节点上都可以访问到。

ELB -> VIP LB -> 容器

#### Ingress + Link

#### 自定义的网络



```bash
### 首先要创建自定义网络：
$ docker network create --driver=overlay --attachable mynet
### 然后再创建服务：
$ docker service create -p 80:80 --network=mynet --name nginx nginx
```
docker pull www.chengchaos.cn:8983/fma/my-eureka-service:latest


自定义网络使得每个服务都可以通过名字来发现彼此，而不再需要 link 操作了。

### 服务更新

```bash
$ docker service update ...
```

### 服务扩缩容

```bash
$ docker service scale ...
```


```bash
docker network ls 


bridge bridge
host host
none null

docker swarm init --advertise-addr 192.168.1.13:2377


docker node ls

docker node promote server02



docker service create --name <service-name> <镜像名> 

docker service create --name test1 alpine ping www.baidu.com

docker service ls

docker service inspect test1

docker service logs test1

docker service create --name nginx --detach=false nginx 

docker service update --publish-add 8080:80 --detach=false nginx

docker service scale nginx=3 --detach=false

docker service ps test1


```



