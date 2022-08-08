package com.xd.io.bio;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

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
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(PORT);
            System.out.println("服务器开始监听端口：" + PORT);
            while (true) {
                // 第一次阻塞，等待客户端连接
                // 阻塞时不会占用cpu资源，反而会释放cpu资源
                Socket socket = serverSocket.accept();
                System.out.println("接收一个客户端的请求");
                InputStream in = socket.getInputStream();
                int length;
                byte[] buffer = new byte[1024];
                // 第二次阻塞，等待客户端发送数据
                // 当有两个连接进来时，由于第一个连接会在此处阻塞，所以不会打印第二个连接输入的内容
                while ((length = in.read(buffer)) != -1) {
                    String str = new String(buffer, 0, length);
                    System.out.println("读取到客户端的输入字符串：" + str);
                }
                in.close();
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                assert serverSocket != null;
                serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void server2() {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(PORT);
            System.out.println("服务器开始监听端口：" + PORT);
            while (true) {
                // 第一次阻塞，等待客户端连接
                // 阻塞时不会占用cpu资源，反而会释放cpu资源
                final Socket socket = serverSocket.accept();
                System.out.println("接收一个客户端的请求");
                new Thread(() -> {
                    try {
                        InputStream in = socket.getInputStream();
                        int length;
                        byte[] buffer = new byte[1024];
                        // 第二次阻塞，等待客户端发送数据
                        // 当有两个连接进来时，由于第一个连接会在此处阻塞，所以不会打印第二个连接输入的内容
                        while ((length = in.read(buffer)) != -1) {
                            String str = new String(buffer, 0, length);
                            System.out.println("读取到客户端的输入字符串：" + str);
                        }
                        in.close();
                        socket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                assert serverSocket != null;
                serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}