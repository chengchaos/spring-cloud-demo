#!/usr/bin/env bash
spark-submit \
--class cn.chengchaos.SqlContextApp \
--master local[2] \
/home/chengchao/lib/my-scala-01.jar \
/home/chengchao/files/people.json