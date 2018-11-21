package cn.chengchaos.akka

import akka.actor.Actor

//import akka.actor.Actor
class PingActor extends Actor {
//  override def receive: Receive =
  override def receive: Receive = {
    case "" => None
    case _ => "kao"
  }
}
