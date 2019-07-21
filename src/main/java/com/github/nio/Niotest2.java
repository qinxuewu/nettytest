package com.github.nio;

import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class Niotest2 {
    public static void main(String[] args) throws  Exception{
        // 创一个文件输入流
        FileInputStream fileInputStream=new FileInputStream("niotext2.txt");
        // 获取通道
        FileChannel channel=fileInputStream.getChannel();
        // 创建一个缓存区 大小512字节
        ByteBuffer byteBuffer=ByteBuffer.allocate(512);

        // 通道读取缓存区
        channel.read(byteBuffer);
        //  反转操作
        byteBuffer.flip();
        while (byteBuffer.remaining()>0){
            byte b=byteBuffer.get();
            System.out.println((char)b);
        }

    }
}
