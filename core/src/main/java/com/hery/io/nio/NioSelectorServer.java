package com.hery.io.nio;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @ClassName NioSelectorServer
 * @Description nio 多路复用
 * NIO 有三大核心组件： Channel(通道)， Buffer(缓冲区)，Selector(多路复用器)
 * 1、channel 类似于流，每个 channel 对应一个 buffer缓冲区，buffer 底层就是个数组
 * 2、channel 会注册到 selector 上，由 selector 根据 channel 读写事件的发生将其交由某个空闲的线程处理
 * 3、NIO 的 Buffer 和 channel 都是既可以读也可以写
 * @Date 2021/7/21 14:55
 * @Author yongheng
 * @Version V1.0
 **/
public class NioSelectorServer {
    private static final Logger logger = LoggerFactory.getLogger(NioSelectorServer.class);

    public static void main(String[] args) throws IOException {
        // 创建NIO ServerSocketChannel
        ServerSocketChannel serverSocket = ServerSocketChannel.open();
        serverSocket.socket().bind(new InetSocketAddress(9000));

        // 设置ServerSocketChannel为非阻塞
        serverSocket.configureBlocking(false);
        // 打开Selector处理Channel，即创建epoll
        Selector selector = Selector.open();
        // 把ServerSocketChannel注册到selector上，并且selector对客户端accept连接操作感兴趣
        serverSocket.register(selector, SelectionKey.OP_ACCEPT);
        logger.info("服务启动成功");

        while (true) {
            // 阻塞等待需要处理的事件发生,让出cpu
            selector.select();
            // 获取selector中注册的全部事件的 SelectionKey 实例

            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            // 遍历SelectionKey对事件进行处理
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                // 如果是OP_ACCEPT事件，则进行连接获取和事件注册
                if (key.isAcceptable()) {
                    ServerSocketChannel server = (ServerSocketChannel) key.channel();
                    SocketChannel socketChannel = server.accept();
                    socketChannel.configureBlocking(false);
                    // 这里只注册了读事件，如果需要给客户端发送数据可以注册写事件
                    socketChannel.register(selector, SelectionKey.OP_READ);
                    logger.info("客户端连接成功");

                } else if (key.isReadable()) {
                    // 如果是OP_READ事件，则进行读取和打印
                    SocketChannel socketChannel = (SocketChannel) key.channel();
                    ByteBuffer byteBuffer = ByteBuffer.allocate(128);
                    int len = socketChannel.read(byteBuffer);
                    // 如果有数据，把数据打印出来
                    if (len > 0) {
                        logger.info("接收到消息，msg:{}", new String(byteBuffer.array()));
                    } else if (len == -1) {
                        logger.info("客户端断开连接");
                        socketChannel.close();
                    }
                }
                //从事件集合里删除本次处理的key，防止下次select重复处理
                iterator.remove();
            }
        }
    }
}
