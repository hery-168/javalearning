package com.hery.io;

import java.io.IOException;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

/**
 * @ClassName SocketClient
 * @Description bio 客户端
 * @Date 2021/7/21 14:36
 * @Author yongheng
 * @Version V1.0
 **/
public class SocketClient {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 9000);
        // 向服务端发送数据
        socket.getOutputStream().write("hello server".getBytes(StandardCharsets.UTF_8));
        socket.getOutputStream().flush();
        System.out.println("发送数据完毕");

        byte[] bytes = new byte[1024];
        // 接受server 发回的数据
        int read = socket.getInputStream().read(bytes);
        System.out.println("接收到服务端数据 为msg:" + new String(bytes));
        socket.close();
    }
}
