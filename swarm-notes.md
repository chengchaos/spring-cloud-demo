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

首先要创建自定义网络：

```bash
$ docker network create --driver=overlay --attachable mynet
```
