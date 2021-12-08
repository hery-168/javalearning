package com.hery.akka.rpc

/**
 * @ClassName RemoteMessage
 * @Description TODO
 * @Date 2021/12/8 17:38
 * @Author yongheng
 * @Version V1.0
 * */
trait RemoteMessage extends Serializable

// TODO_YH : Worker-> Master  注册worker 消息
case class RegisterWorker(id:String,memory:Int,cores:Int) extends RemoteMessage

// TODO_YH : Master-> Worker  注册worker 消息
case class RegisteredWorker(masterUrl:String) extends RemoteMessage

//worker-> worker 不需要网络传输，不用序列化，直接case object
case object SendHeartBeat

// worker - > master
case class HeartBeat(id:String) extends RemoteMessage


//master-> master  master 定时检测心跳，不需要网络传输，不用序列化，直接case object
case object CheckTimeOutWorker