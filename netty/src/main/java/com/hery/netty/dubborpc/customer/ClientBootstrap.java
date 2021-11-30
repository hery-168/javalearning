package com.hery.netty.dubborpc.customer;

import com.hery.netty.dubborpc.netty.NettyClient;
import com.hery.netty.dubborpc.publicinterface.HelloService;

import java.util.Random;

public class ClientBootstrap {


    //这里定义协议头
    public static final String providerName = "HelloService#hello#";

    public static void main(String[] args) throws  Exception{

        //创建一个消费者
        NettyClient customer = new NettyClient();

        //创建代理对象
        HelloService service = (HelloService) customer.getBean(HelloService.class, providerName);
        Random random = new Random();
        for (;; ) {
            Thread.sleep(5 * 1000);
            //通过代理对象调用服务提供者的方法(服务)
            String res = service.hello("你好 dubbo,"+random.nextInt(50));
            System.out.println("调用的结果 res= " + res);
        }
    }
}
