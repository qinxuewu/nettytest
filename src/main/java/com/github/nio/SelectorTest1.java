package com.github.nio;


import org.omg.PortableServer.POA;

import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 *
 *  在 Netty 中, 一个 EventLoop 需要负责两个工作,
 *      第一个是作为 IO 线程, 负责相应的 IO 操作;
 *      第二个是作为任务线程, 执行 taskQueue 中的任务
 *
 *
 * @author qxw
 * @version 1.00
 * @time  23/7/2019 下午 5:27
 */
public class SelectorTest1 {


    public static void main(String[] args) throws  Exception {
        // 定义5个端口号
        int [] prots=new int[5];
        prots[0]=5000;
        prots[1]=5001;
        prots[2]=5002;
        prots[3]=5003;
        prots[4]=5004;
        
        // 打开Selector
        Selector selector=Selector.open();

        for (int i = 0; i < prots.length; ++i) {
                ServerSocketChannel serverSocketChannel=ServerSocketChannel.open();
                // 非阻塞模式
                serverSocketChannel.configureBlocking(false);
                ServerSocket serverSocket=serverSocketChannel.socket();
                InetSocketAddress address=new InetSocketAddress(prots[i]);
                // 绑定端口号
                serverSocket.bind(address);

                // 将当前selector 注册到channel  事件为接收连接
                serverSocketChannel.register(selector,SelectionKey.OP_ACCEPT);
                System.out.println("监听端口： "+prots[i]);
        }

        while (true){
            int numbers=selector.select();
            System.out.println("numbrs: "+numbers);
            Set<SelectionKey> selectionKeys=selector.selectedKeys();
            Iterator<SelectionKey> iter=selectionKeys.iterator();

            while (iter.hasNext()){
                SelectionKey selectionKey=iter.next();
                //  是否连接
                if(selectionKey.isAcceptable()) {
                        ServerSocketChannel serverSocketChannel = (ServerSocketChannel) selectionKey.channel();
                        SocketChannel socketChannel = serverSocketChannel.accept(); // 建立连接
                        // 非阻塞
                        socketChannel.configureBlocking(false);

                        socketChannel.register(selector, SelectionKey.OP_READ);  // 注册 ，读事件

                        iter.remove(); //从当前Selection删除已注册的连接

                        System.out.println("获得客户端的连接：" + socketChannel);
                }else if (selectionKey.isReadable()){
                    // 读取
                    SocketChannel socketChannel=(SocketChannel) selectionKey.channel();

                    int byteRead=0;
                    while (true){
                        ByteBuffer byteBuffer=ByteBuffer.allocate(512);

                        //  clear方法 将 positin 设置为0, 将 limit 设置为 capacity;在一个已经写满数据的 buffer 中, 调用 clear, 可以从头读取 buffer 的数据.
                        byteBuffer.clear();
                        int read=socketChannel.read(byteBuffer);

                        if(read<=0){
                            break;  //读完了
                        }

                        // 进行写  返回给客户端
                        byteBuffer.flip();
                        socketChannel.write(byteBuffer);

                        byteRead+=read;
                    }
                    System.out.println("读取 ："+byteRead +" ,来自客户端:"+socketChannel);
                    iter.remove();
                }
            }

        }

    }
}
