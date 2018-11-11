#!/usr/bin/env sh
#set -o nounset
#set -o errexit
echo ">>>>>>>>> 显示容器中的环境变量 :) "
printenv
echo ">>>>>>>>> 显示 JAVA_OPTS 变量值 :) "
echo "可以通过: -e JAVA_OPTS='-server -Xms128m -Xmx256m ' 方式传递 java 启动参数"
JAVA_OPTS="${JAVA_OPTS}"
echo "JAVA_OPTS=${JAVA_OPTS}"
echo ">>>>>>>>> 显示启动命令 :) "
echo "可以通过命令行传递参数. 例如 --server.port=8080 "

function do_start {
    #
    # 内部变量保存 jar 路径和  log 路径
    # 每个文件都需要修改这个两个变量
    #
    local run_jar_path='/my-eureka-1.0.1.jar'
    local run_log_path='/logs/my-eureka.log'
    # 启动命令:
    local exec_cmd="java -jar $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom ${run_jar_path} $* >> ${run_log_path} 2>&1"
    echo ${exec_cmd}
    echo ">>>>>>>>> 启动了 ;) "
    ${exec_cmd}
}

do_start $*



