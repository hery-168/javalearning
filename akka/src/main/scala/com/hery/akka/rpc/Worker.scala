package com.hery.akka.rpc

import akka.actor.{Actor, ActorSelection, ActorSystem, Props}
import com.typesafe.config.ConfigFactory

import java.util.UUID
import scala.concurrent.duration._

/**
 * @ClassName Worker
 * @Description worker 节点
 *              1.与master建立连接
 *              2.拿到master代理
 *              3.向master发送消息
 *              4.接收master的反馈
 * @Date 2021/12/8 14:53
 * @Author yongheng
 * @Version V1.0
 * */
class Worker(val masterHost: String, val masterPort: Int, val memory: Int, val cores: Int) extends Actor {

  var masterRef: ActorSelection = _
  val workerId = UUID.randomUUID().toString
  val HEART_INTERVAL = 10000
  println("创建Worker对象...")

  // TODO_YH : 建立连接
  override def preStart(): Unit = {
    //与Master建立连接，拿到master引用
    /** ***********************************************
     * TODO_YH
     * 注释：path 写法规范
     * akka.tcp://{ActorSystemName}@{MasterHost}:{MasterPort}/user/{ActorName}
     */
    masterRef = context.actorSelection(s"akka.tcp://MasterSystem@$masterHost:$masterPort/user/master")
    //向Master发送注册消息
    masterRef ! RegisterWorker(workerId, memory, cores)
  }

  override def receive: Receive = {

    case RegisteredWorker(masterUrl) => {
      println("收到Master 的URL" + masterUrl)
      // 导入隐式转换
      import context.dispatcher
      // 启动定时任务，发送心跳
      context.system.scheduler.schedule(0 millis, HEART_INTERVAL millis, self, SendHeartBeat)

    }
    case SendHeartBeat => {
      println("send heart beat to master")
      masterRef ! HeartBeat(workerId)
    }
  }
}

object Worker {
  def main(args: Array[String]): Unit = {

    //    val host = args(0)
    //    val port = args(1).toInt
    //    val masterHost = args(2)
    //    val masterPort = args(3).toInt

    val host = "127.0.0.1"
    val port = 6666
    val masterHost = "127.0.0.1"
    val masterPort = 8888

    val memory = 8
    val cores = 16

    val configStr =
      s"""
         |akka.actor.provider = "akka.remote.RemoteActorRefProvider"
         |akka.remote.netty.tcp.hostname = "$host"
         |akka.remote.netty.tcp.port = "$port"
      """.stripMargin
    //传入配置参数获取配置
    val config = ConfigFactory.parseString(configStr)
    //获取ActorSystem，指定名称和配置
    val workerSystem = ActorSystem("WorkerSystem", config)
    //创建Actor
    val actor = workerSystem.actorOf(Props(new Worker(masterHost, masterPort, memory, cores)), "worker")
    //    workerSystem.awaitTermination()
  }
}
