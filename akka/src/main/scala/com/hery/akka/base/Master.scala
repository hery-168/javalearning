package com.hery.akka.base

import akka.actor.{Actor, ActorSystem, Props}
import com.typesafe.config.ConfigFactory
/**
 * @ClassName Master
 * @Description 主节点
 * @Date 2021/12/8 14:39
 * @Author yongheng
 * @Version V1.0
 * */
class Master extends Actor {

  println("Master 构造器被执行")

  override def preStart(): Unit = {
    println("preStart 被执行")
  }

  // TODO_YH : 用于接收消息
  override def receive: Receive = {
    case "connect" => {
      println("receive 被执行， connect 方法被执行")
//      val ref = sender()
      // TODO_YH : 发送消息 sender 就是worker的引用，向worker 发送reply 消息
      sender ! "reply"
    }
    case "hello" => {
      println("receive 被执行，hello 方法被执行")
    }
  }
}

object Master {
  def main(args: Array[String]): Unit = {
    // ActorSystem  负责创建 监控 actor

    //连接地址和端口号
//    val host = args(0)
//    val port = args(1).toInt

    val host = "127.0.0.1"
    val port = 8888

    val configStr =
      s"""
         |akka.actor.provider = "akka.remote.RemoteActorRefProvider"
         |akka.remote.netty.tcp.hostname = "$host"
         |akka.remote.netty.tcp.port = "$port"
      """.stripMargin
    //传入配置参数获取配置
    val config = ConfigFactory.parseString(configStr)

    //获取ActorSystem，指定名称和配置
    val actorSystem = ActorSystem("MasterSystem", config)

    //创建Actor
    val master = actorSystem.actorOf(Props(new Master), "master")
    //自己给自己发送给消息
        master ! "hello"


  }
}
