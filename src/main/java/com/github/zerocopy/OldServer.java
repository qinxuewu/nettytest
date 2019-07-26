package com.github.zerocopy;

import java.io.DataInputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 非零拷贝案列 文件发送
 * @author qxw
 * @version 1.00
 * @time  26/7/2019 下午 6:09
 */
public class OldServer {

    public static void main(String[] args) throws  Exception{
        ServerSocket serverSocket=new ServerSocket(8899);
        while (true){
            // 建立连接
            Socket socket=serverSocket.accept();
            // 获取socker输入流
            DataInputStream dataInputStream=new DataInputStream(socket.getInputStream());
            try {
                // 指定字节数据读取的长度
                byte[] bytesArray=new byte[4096];
                // 循环读取
                while (true){
                    // 实际读到的字节数
                    int readCount=dataInputStream.read(bytesArray);

                    if(-1 == readCount){
                        break;
                    }

                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
