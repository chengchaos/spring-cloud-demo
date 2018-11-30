package cn.chengchaos.akka.mydb


import akka.actor.{Actor, Status}
import akka.event.Logging

import scala.collection.mutable

class ServerActor extends  Actor {

  private val log = Logging(context.system, this)

  private val map = new mutable.HashMap[String, Object]()


  override def receive = {

    case SetRequest(key, value) => {
      log.info("received SetRequest - key: {}, value: {}", key, value)
      map.put(key, value)
      sender() ! Status.Success
    }
    case GetRequest(key) => {
      log.info("received GetRequest - key: {}", key)
      val response: Option[Object] = map.get(key)
      response match {
        case Some(x) => sender() ! x
        case None => sender() ! Status.Failure(new KeyNotFoundException(key))
      }
    }
    case _ => Status.Failure(new ClassNotFoundException)
  }

}
