package com.github.nio;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;

/**
 *  关于Buffer的Scattering与Gathering  两个特性，发散和聚合
 *
 *  发散是指从channel中读取数据的时候可以传入buffer的数组，并依次将buffer数组写满，直到channel中无数据
 *
 *  聚合是指往channel中写入数据时可以传入buffer数据，并依次将buffer数据中的数据读入channel中，直到无数据
 *
 * @author qinxuewu
 * @create 19/7/22下午9:56
 * @since 1.0.0
 */


public class Niotest11 {
    public static void main(String[] args)  throws  Exception{
        ServerSocketChannel serverSocketChannel=ServerSocketChannel.open();
        InetSocketAddress address=new InetSocketAddress(8899);

        serverSocketChannel.socket().bind(address);

        int messageLength=2+3+4;
        ByteBuffer [] byteBuffers=new ByteBuffer[3];

        byteBuffers[0]=ByteBuffer.allocate(2);
        byteBuffers[1]=ByteBuffer.allocate(3);
        byteBuffers[2]=ByteBuffer.allocate(4);

        SocketChannel socketChannel=serverSocketChannel.accept();

        //这样写主要是为了保持连接不断开，并且能一直读取channel的数据
        while (true){
            int byreRead =0;
            // 如果 读的字节 少于9个字节则执行
            while (byreRead< messageLength){
                long r=socketChannel.read(byteBuffers);
                byreRead +=r ;

                // 当前读了多少个字节
                System.out.println("byreRead :"+byreRead);

                Arrays.asList(byteBuffers).stream().map(buffer -> "position :" +buffer.position()
                + "limit :"+buffer.limit()).forEach(System.out::println);
            }

            Arrays.asList(byteBuffers).forEach( buffer -> {
                buffer.flip();
            });

            long byteWriter =0;

            while (byteWriter < messageLength) {
                long r=socketChannel.write(byteBuffers);

                byteWriter += r;
            }

            Arrays.asList(byteBuffers).forEach(buffer -> {
                buffer.flip();
            });

            System.out.println("byreRead :" +byreRead +" ,byteWriter :" +byteWriter);
        }
    }
}
