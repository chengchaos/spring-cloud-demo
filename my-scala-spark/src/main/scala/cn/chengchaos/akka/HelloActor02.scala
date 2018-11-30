package cn.chengchaos.akka

class HelloActor02(myName: String) extends akka.actor.Actor {

  override def receive: Receive = {
    case "hello" => println(s"hello from $myName")
    case _ => println(s"'huh?', said $myName")
  }
}
