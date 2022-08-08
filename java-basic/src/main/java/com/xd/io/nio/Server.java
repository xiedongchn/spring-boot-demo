package com.xd.io.nio;

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
 * BIO模型服务端
 *
 * @author xd
 */
public class Server {

    private static final int PORT = 8585;

    public static void main(String[] args) {
        // 单线程同步阻塞模式，即使多个连接进来，一次只能处理一个请求
        server1();
        // 多线程，一次可以处理多个请求，单个线程是阻塞的
        //server2();
    }

    public static void server1() {
        try {
            // 创建ServerSocketChannel通道，绑定监听端口8585
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            // 绑定监听端口
            serverSocketChannel.bind(new InetSocketAddress(PORT));
            // 设置为非阻塞模式
            serverSocketChannel.configureBlocking(false);
            // 将ServerSocketChannel注册到选择器Selector中
            Selector selector = Selector.open();
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            while (true) {
                // 选择事件，这里会阻塞
                // 如果没有任何事件可以处理，该方法会处于阻塞状态
                selector.select();
                Set<SelectionKey> keys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = keys.iterator();
                System.out.println("处理事件：" + keys.size());
                while (iterator.hasNext()) {
                    SelectionKey key = iterator.next();
                    // 删除key，防止重复处理
                    iterator.remove();
                    if (key.isReadable()) {
                        System.out.println("数据读取事件");
                        SocketChannel channel = (SocketChannel) key.channel();
                        ByteBuffer dest = ByteBuffer.allocate(1024);
                        int read = channel.read(dest);
                        System.out.println(new String(dest.array(), 0, read));
                    } else if (key.isAcceptable()) {
                        System.out.println("连接事件");
                        SocketChannel channel = serverSocketChannel.accept();
                        channel.configureBlocking(false);
                        // 接收到请求，将连接注册到选择器中
                        channel.register(selector, SelectionKey.OP_READ);
                    } else if (key.isWritable()) {
                        System.out.println("写数据事件");
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}