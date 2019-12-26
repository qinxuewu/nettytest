package com.github.bio;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BioServer {

    public static void main(String[] args) throws IOException {

        // 创建一个线程池  如果客户度有连接 就创建一个线程 与之通讯
        ExecutorService executorService= Executors.newCachedThreadPool();

        ServerSocket serverSocket=new ServerSocket(8888);

        while (true){
            System.out.println("等待客户连接.....");
            final Socket socket=serverSocket.accept();

             // 如果有连接来 就创建一个线程 与之通讯
            executorService.execute(new Runnable() {
                @Override
                public void run() {

                }
            });
        }
    }

    private  static  void  handler(Socket socket) throws IOException {
        try {
            System.out.println(Thread.currentThread().getName()+" 线程开始通讯");
            byte[] bytes=new byte[1024];

            InputStream inputStream=socket.getInputStream();
            while (true){
                System.out.println("read.........");
                int read=inputStream.read(bytes);
                if(read!=-1){
                    System.out.println(new String(bytes,0,read));
                }else {
                    break;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            socket.close();
        }
    }
}
