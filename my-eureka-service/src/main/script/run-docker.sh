#!/usr/bin/env bash
#
# 启动 docker
#
DOCKER_IMAGE_NAME='sinogold/sinogold-management'
DOCKER_CONTAINER_NAME='sinogold-management'
docker stop ${DOCKER_CONTAINER_NAME}
docker rm ${DOCKER_CONTAINER_NAME}

SERVER_RUN_PORT=8080
SERVER_EXPORT_PORT=9083
echo "DOCKER_IMAGE_NAME > ${DOCKER_IMAGE_NAME}"
echo "DOCKER_CONTAINER_NAME > ${DOCKER_CONTAINER_NAME}"
echo "SERVER_RUN_PORT > ${SERVER_RUN_PORT}"
echo "SERVER_EXPORT_PORT > ${SERVER_EXPORT_PORT}"

docker run -d --name ${DOCKER_CONTAINER_NAME} --net guojin \
    -v /data/work/logs:/logs \
    -v /data/work/data/management:/data \
    -e JAVA_OPTS=' -server -Xmx1024m ' \
    -p ${SERVER_EXPORT_PORT}:${SERVER_RUN_PORT} \
    ${DOCKER_IMAGE_NAME} \
    --server.port=${SERVER_RUN_PORT} $*