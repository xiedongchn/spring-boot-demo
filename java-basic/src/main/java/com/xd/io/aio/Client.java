package com.xd.io.aio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.Scanner;

/**
 * AIO模型服务端
 *
 * @author xd
 */
public class Client {

    private static final int PORT = 8585;

    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        System.out.println("请输入客户端编号：");
        int no = sc.nextInt();
        AsynchronousSocketChannel socketChannel = AsynchronousSocketChannel.open();
        socketChannel.connect(new InetSocketAddress("127.0.0.1", PORT));
        ByteBuffer buffer;
        String str;
        while (true) {
            System.out.println("客户端：" + no + "请输入要发送的内容：");
            str = sc.next();
            if ("quit".equalsIgnoreCase(str)) {
                break;
            }
            byte[] bytes = (no + "：" + str).getBytes();
            buffer = ByteBuffer.allocate(bytes.length);
            buffer.put(bytes);
            buffer.flip();
            socketChannel.write(buffer);
        }
    }
}