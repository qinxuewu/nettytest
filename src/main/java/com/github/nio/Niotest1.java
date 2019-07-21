package com.github.nio;

import java.nio.IntBuffer;
import java.security.SecureRandom;

public class Niotest1 {
    public static void main(String[] args) {
        // 创建安一个int缓存区 大小为 10
        IntBuffer buffer=IntBuffer.allocate(10);


        for (int i = 0; i <10 ; i++) {
            int number=new SecureRandom().nextInt(20);
            buffer.put(number);
        }
        buffer.flip();

        while (buffer.hasRemaining()){
            System.out.println(buffer.get());
        }
    }
}
