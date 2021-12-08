package com.hery.akka.rpc

import akka.actor.{Actor, ActorSystem, Props}
import com.typesafe.config.ConfigFactory

import scala.collection.mutable
import scala.concurrent.duration._

/**
 * @ClassName Master
 * @Description 主节点
 * @Date 2021/12/8 14:39
 * @Author yongheng
 * @Version V1.0
 * */
class Master(val host: String, val port: Int) extends Actor {


  // TODO_YH : 保存worker信息
  val idToWorker = new mutable.HashMap[String, WorkInfo]()
  // WorkInfo set 用于后期remove
  val workers = new mutable.HashSet[WorkInfo]()

  // 心跳定时检测周期
  val CHECK_HEART_INTERVAL = 15000


  override def preStart(): Unit = {
    // 导入隐式转换
    import context.dispatcher
    context.system.scheduler.schedule(0 millis, CHECK_HEART_INTERVAL millis, self, CheckTimeOutWorker)
  }

  /**
   * 用于接收消息
   *
   * @return
   */
  override def receive: Receive = {
    case RegisterWorker(id, memory, cores) => {
      println("master  收到注册消息")
      // TODO_YH : 判断是否注册过，没有注册,就保存到内存中，也可以进行在zk中持久化或文件系统
      if (!idToWorker.contains(id)) {
        val workInfo = new WorkInfo(id, memory, cores)
        //idToWorker.put(id, workInfo)
        idToWorker(id) = workInfo
        workers += workInfo
        // 注册成功，给worker发送注册成功的消息
        sender ! RegisteredWorker(s"akka.tcp://MasterSystem@$host:$port/user/master")
      }
    }
    case HeartBeat(id) => {
      if (idToWorker.contains(id)) {
        val workerInfo = idToWorker(id)
        val currentTime = System.currentTimeMillis()
        workerInfo.lastHeartBeatTime = currentTime
      }
    }
    case CheckTimeOutWorker => {
      val currentTime = System.currentTimeMillis()
      val toRemove = workers.filter(x => currentTime - x.lastHeartBeatTime > CHECK_HEART_INTERVAL)
      for (w <- toRemove) {
        workers -= w
        idToWorker -= w.id
      }
      println("worker 存活的数量：" + workers.size)
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
    val master = actorSystem.actorOf(Props(new Master(host, port)), "master")
    //自己给自己发送给消息
    //master ! "hello"


  }
}
