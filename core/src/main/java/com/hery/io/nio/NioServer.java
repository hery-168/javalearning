package com.hery.io.nio;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @ClassName NioServer
 * @Description nio server
 * 同步非阻塞，服务器实现模式为一个线程可以处理多个请求(连接)，客
 * 户端发送的连接请求都会注册到多路复用器selector上，
 * 多路复用 器轮询到连接有IO请求就进行处理，JDK1.4开始引入。
 * <p>
 * 应用场景： NIO方式适用于连接数目多且连接比较短（轻操作） 的架构， 比如聊天服务器， 弹幕系统， 服务器间通讯，编程比较复杂
 * 如果连接数太多的话，会有大量的无效遍历，假如有10000个连接，其中只有1000个连接有写数据，
 * 但是由于其他9000个连接并 没有断开，我们还是要每次轮询遍历一万次，其中有十分之九的遍历都是无效的，这显然不是一个让人很满意的状态
 * @Date 2021/7/21 14:40
 * @Author yongheng
 * @Version V1.0
 **/
public class NioServer {

    private static final Logger logger = LoggerFactory.getLogger(NioServer.class);
    /**
     * 保存客户端连接
     */
    static List<SocketChannel> channelList = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        // 创建NIO ServerSocketChannel,与BIO的serverSocket类似
        ServerSocketChannel serversocket = ServerSocketChannel.open();
        serversocket.socket().bind(new InetSocketAddress(9000));

        // 设置ServerSocketChannel为非阻塞
        serversocket.configureBlocking(false);
        logger.info("服务端启动成功");
        while (true) {
            // 非阻塞模式accept方法不会阻塞，否则会阻塞
            // NIO的非阻塞是由操作系统内部实现的，底层调用了linux内核的accept函数
            SocketChannel socketChannel = serversocket.accept();
            if (socketChannel != null) {
                logger.info("连接成功");
                // 设置SocketChannel为非阻塞
                socketChannel.configureBlocking(false);
                // 保存客户端连接在List中
                channelList.add(socketChannel);
            }
            Iterator<SocketChannel> iterator = channelList.iterator();
            while (iterator.hasNext()) {
                SocketChannel sc = iterator.next();
                ByteBuffer byteBuffer = ByteBuffer.allocate(128);
                // 非阻塞模式read方法不会阻塞，否则会阻
                int read = sc.read(byteBuffer);
                if (read > 0) {
                    logger.info("接收到信息为：{}", new String(byteBuffer.array()));

                } else if (read == -1) {// 如果客户端断开，把socket从集合中去掉
                    iterator.remove();
                    logger.info("客户端断开连接");
                }
            }

        }
    }
}
