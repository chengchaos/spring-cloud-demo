FROM www.chengchaos.cn:8983/fma/openjdk8_jdk-alpine
ADD my-eureka-service.jar my-eureka-service.jar
ADD start.sh start.sh
RUN chmod u+x start.sh
RUN mkdir -p /logs/
ENTRYPOINT ["/start.sh"]