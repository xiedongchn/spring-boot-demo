package com.xd.io.bio;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

/**
 * BIO模型服务端
 *
 * @author xd
 */
public class Client {
    private static final int PORT = 8585;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("请输入客户端编号：");
        int no = sc.nextInt();
        Socket socket = null;
        OutputStream out = null;
        try {
            System.out.println("客户端：" + no + "开始链接服务器");
            socket = new Socket("127.0.0.1", PORT);
            System.out.println("客户端：" + no + "连接服务器成功");
            out = socket.getOutputStream();
            while (true) {
                System.out.println("请输入要发送的字符(输入quit表示结束)：");
                String str = sc.next();
                if (str != null && "quit".equalsIgnoreCase(str.trim())) {
                    break;
                }
                out.write((no + "：" + str).getBytes());
            }
            System.out.println("客户端连接中断");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                assert out != null;
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}