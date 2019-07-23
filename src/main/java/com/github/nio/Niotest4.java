package com.github.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;


/**
 * 通过NIO读取文件的三个步骤
 *  1： 从FileInputStream获取FileChannel对象
 *  2 创建 buffer
 *  3 将数据从Channel读取到Buffer中
 */
public class Niotest4 {

    public static void main(String[] args) throws  Exception {
        // 创建文件输入流和输出流
        FileInputStream fileInputStream=new FileInputStream("input.txt");
        FileOutputStream fileOutputStream=new FileOutputStream("out.txt");

        // 创建一个读取通道 和写入通道
        FileChannel inputChannel=fileInputStream.getChannel();
        FileChannel outChannel=fileOutputStream.getChannel();

        ByteBuffer buffer=ByteBuffer.allocate(512);

        while (true){
            // 用来初始化缓存空间，例如读取文件时将文件内容置入缓存时要先执行此方法
            buffer.clear();

            int read=inputChannel.read(buffer);

            System.out.println("read: "+read);

            if (-1== read){
                break;
            }
            // 反转操作
            buffer.flip();
            outChannel.write(buffer);
        }

        inputChannel.close();
        outChannel.close();
    }
}
