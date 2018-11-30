package cn.chengchaos.akka

import akka.actor.Actor

case object  AskNameMessage

class AkkaAsk01 extends Actor {
  override def receive: Receive = {
    case AskNameMessage => {
      // respond to the 'ask' request
      sender ! "Fred"
    }
    case _ => println("that was unexpected")
  }


}
