
Quick Start：

https://cloud.spring.io/spring-cloud-netflix/


项目参考：

https://github.com/spring-cloud-samples/eureka



Harbor

Harbor12345*
Over201*

在第一台主机上执行

```sh
$ docker login www.chengchaos.cn:8983
Username: chengchao
$ docker swarm leave --force
$ docker swarm init
Swarm initialized: current node (mupztugdpfsnfymgm80pmlc8e) is now a manager.

To add a worker to this swarm, run the following command:

    docker swarm join --token SWMTKN-1-38rhxqq4itu8wpa74hwly46kcwfvebbfchl8jhugzbtcat5e2t-7llh4g6e4lr154g45f1rspge7 192.168.88.143:2377

To add a manager to this swarm, run 'docker swarm join-token manager' and follow the instructions.
```

在其他主机上执行

```sh
$ docker login www.chengchaos.cn:8983
Username: chengchao
$ docker swarm leave
$ docker swarm join --token SWMTKN-1-38rhxqq4itu8wpa74hwly46kcwfvebbfchl8jhugzbtcat5e2t-7llh4g6e4lr154g45f1rspge7 192.168.88.143:2377

```