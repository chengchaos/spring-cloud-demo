package cn.chengchaos.akka.mydb

class MyData {


}

case class SetRequest(key: String, value: Object)

case class GetRequest(key: String)

case class KeyNotFoundException(key: String) extends Exception