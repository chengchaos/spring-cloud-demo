#!/usr/bin/env bash
spark-submit \
--class cn.chengchaos.HiveContextApp \
--jars /home/chengchao/software/mysql-conn.jar \
--master local[2] \
/home/chengchao/lib/my-scala-01.jar \
/home/chengchao/files/people.json