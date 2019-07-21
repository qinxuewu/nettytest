package com.github.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class Niotest3 {

    public static void main(String[] args) throws Exception {
        FileOutputStream fileOutputStream=new FileOutputStream("niotext3.txt");

        FileChannel fileChannel=fileOutputStream.getChannel();

        ByteBuffer byteBuffer=ByteBuffer.allocate(512);

        byte [] msg="hellow word nio text3".getBytes();
        for (int i = 0; i <msg.length ; i++) {
                byteBuffer.put(msg[i]);
        }
        byteBuffer.flip();

        fileChannel.write(byteBuffer);
        fileOutputStream.close();

    }
}
