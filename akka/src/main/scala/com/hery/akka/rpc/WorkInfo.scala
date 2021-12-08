package com.hery.akka.rpc

/**
 * @ClassName WorkInfo
 * @Description TODO
 * @Date 2021/12/8 17:50
 * @Author yongheng
 * @Version V1.0
 * */
class WorkInfo(val id: String, val memory: Int, val core: Int) {
  // TODO_YH : 上一次心跳
  var lastHeartBeatTime: Long = _
}
