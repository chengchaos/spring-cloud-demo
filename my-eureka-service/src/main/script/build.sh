#!/usr/bin/env bash
#
# 构建 Eureka 模块
#

cd /home/chengchao/git/github/spring-cloud-demo/
git pull

echo "构建 my-eureka-service"

PROFILE='test'
if [ -n "$1" ]; then
    PROFILE=$1
fi
echo "mvn -P${PROFILE} clean package docker:build -Dmaven.test.skip=true"

cd /home/chengchao/git/github/spring-cloud-demo/my-eureka-service
mvn -U -P${PROFILE} clean package docker:build -Dmaven.test.skip=true
