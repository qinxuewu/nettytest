package com.github.nio;

import java.nio.ByteBuffer;

/**
 *  只读buffer
 * @author qxw
 * @version 1.00
 * @time  22/7/2019 下午 5:26
 */
public class Niotest5 {

    public static void main(String[] args) {
        ByteBuffer buffer=ByteBuffer.allocate(512);

        for (int i = 0; i < buffer.capacity(); i++) {
                buffer.put((byte)i);
        }

        ByteBuffer readbuffer=buffer.asReadOnlyBuffer();  //只读buffer

        // 不会有任何输出
        System.out.println(readbuffer.getClass());
    }
}
