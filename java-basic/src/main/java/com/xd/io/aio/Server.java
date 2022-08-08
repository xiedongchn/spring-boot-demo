package com.xd.io.aio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * AIO模型服务端
 *
 * @author xd
 */
public class Server {

    private static final int PORT = 8585;

    public static void main(String[] args) throws IOException {
        server2();
    }

    // 基于Future的阻塞的实现方式，通常不是这么做的
    public static void server1() throws IOException, ExecutionException, InterruptedException {
        // 创建一个SocketChannel
        AsynchronousServerSocketChannel asynchronousServerSocketChannel = AsynchronousServerSocketChannel.open();
        // 绑定监听端口
        asynchronousServerSocketChannel.bind(new InetSocketAddress("127.0.0.1", PORT));
        while (true) {
            // Future方式监听
            Future<AsynchronousSocketChannel> future = asynchronousServerSocketChannel.accept();
            // 此处是阻塞的，直到有请求进来
            AsynchronousSocketChannel socketChannel = future.get();
            System.out.println("接收到请求。。。");
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            // 循环读取数据
            while (socketChannel.isOpen()) {
                // 这里也是阻塞的
                Future<Integer> read = socketChannel.read(buffer);
                Integer i = read.get();
                if (i > 0) {
                    System.out.println(new String(buffer.array(), 0, i, StandardCharsets.UTF_8));
                    buffer.clear();
                }
            }
        }
    }

    // 基于回调的方式实现aio
    public static void server2() throws IOException {
        // 创建一个SocketChannel
        final AsynchronousServerSocketChannel asynchronousServerSocketChannel = AsynchronousServerSocketChannel.open();
        // 绑定监听端口
        asynchronousServerSocketChannel.bind(new InetSocketAddress("127.0.0.1", PORT));
        // 开始监听，第一个参数attachment不为null时可循环监听，第二个参数是回调处理器
        asynchronousServerSocketChannel.accept(null, new CompletionHandler<AsynchronousSocketChannel, Void>() {
            // 监听完成时执行的方法
            @Override
            public void completed(AsynchronousSocketChannel result, Void attachment) {
                // 调用accept方法，实现循环监听
                asynchronousServerSocketChannel.accept(null, this);
                // 接收到新的客户端连接时调用，result就是和客户端的连接对话，可以通过result和客户端通信
                System.out.println("accept completed");
                ByteBuffer buffer = ByteBuffer.allocate(1024);
                // 循环读取数据
                while (result.isOpen()) {
                    try {
                        // 这里是阻塞的
                        Future<Integer> read = result.read(buffer);
                        Integer i = read.get();
                        System.out.println(new String(buffer.array(), 0, i, StandardCharsets.UTF_8));
                        buffer.clear();
                    } catch (InterruptedException | ExecutionException e) {
                        e.printStackTrace();
                    }
                }

            }

            // 监听异常时执行的方法
            @Override
            public void failed(Throwable exc, Void attachment) {

            }
        });

        // 因为是异步的，这里read一下，让主线程暂停
        System.in.read();
    }
}