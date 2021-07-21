package com.hery.io.bio;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

/**
 * @ClassName SocketServer
 * @Description bio 演示 同步阻塞模型，一个客户端连接对应一个处理线程
 * 缺点：
 * 1、IO代码里read操作是阻塞操作，如果连接不做数据读写操作会导致线程阻塞，浪费资源
 * 2、如果线程很多，会导致服务器线程太多，压力太大，比如C10K问题
 *
 * 应用场景： BIO 方式适用于连接数目比较小且固定的架构， 这种方式对服务器资源要求比较高， 但程序简单易理解。
 * @Date 2021/7/21 14:28
 * @Author yongheng
 * @Version V1.0
 **/
public class SocketServer {
    public static void main(String[] args) throws IOException {
        // 创建服务端
        ServerSocket serverSocket = new ServerSocket(9000);

        while (true) {
            System.out.println("等待客户端连接");
            // 阻塞方法
            Socket clientSocket = serverSocket.accept();
            System.out.println("客户端已经连接");
            handler(clientSocket);
        }
    }

    /**
     * 处理客户端数据
     *
     * @param clientSocket
     * @throws IOException
     */
    private static void handler(Socket clientSocket) throws IOException {
        byte[] bytes = new byte[1024];
        System.out.println("准备读取数据");
        // 阻塞方法，没有数据可读就阻塞
        int read = clientSocket.getInputStream().read(bytes);
        System.out.println("读取完毕");
        if (read != -1) {
            System.out.println("接收到数据" + new String(bytes, 0, read));

        }
        clientSocket.getOutputStream().write("hello client".getBytes(StandardCharsets.UTF_8));
        clientSocket.getOutputStream().flush();
    }
}
