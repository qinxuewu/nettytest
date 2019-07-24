package com.github.nio;

import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class NioServer {
    // 构造一个map集合存储所有建立连接的客户端
    private  static Map<String, SocketChannel> clientMap=new HashMap<>();

    public static void main(String[] args) throws Exception {
        ServerSocketChannel serverSocketChannel=ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false); // 非阻塞模式  Channel必须是非阻塞的
        ServerSocket serverSocket=serverSocketChannel.socket();
        serverSocket.bind(new InetSocketAddress(8899)); // 绑定端口号

        // 打开Selector
        Selector selector=Selector.open();
        // 将 Channel 注册到 Selector 中, 并设置需要监听的事件  Accept, 即确认事件
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        while (true){
            try {
                // 在刚初始化的Selector对象中，这三个集合都是空的。 通过Selector的select（）方法可以选择已经准备就绪的通道
                // 比如你对读就绪的通道感兴趣，那么select（）方法就会返回读事件已经就绪的那些通道
                /**
                 *  int select()：阻塞到至少有一个通道在你注册的事件上就绪了。
                 *  int select(long timeout)：和select()一样，但最长阻塞时间为timeout毫秒。
                 *  int selectNow()：非阻塞，只要有通道就绪就立刻返回。
                 */
                selector.select();

                // 获取 selected keys; 迭代每个 selected key
                Set<SelectionKey> selectionKeys=selector.selectedKeys();

                selectionKeys.forEach(selectionKey -> {
                    final  SocketChannel client;
                    try {
                        //是否可接受连接，是返回 true
                        if(selectionKey.isAcceptable()){

                            // 返回回该SelectionKey对应的channel。
                            ServerSocketChannel server = (ServerSocketChannel) selectionKey.channel();
                            // 接受与此通道的套接字建立的连接
                            client = server.accept();
                            client.configureBlocking(false);
                            // 注册读事件
                            client.register(selector,SelectionKey.OP_READ);

                            String key="【"+ UUID.randomUUID().toString()+"】,连接上";
                            // 添加客户端链接的到集合中
                            clientMap.put(key,client);
                        }else if(selectionKey.isReadable()){
                            // 通道可读
                            client=(SocketChannel)selectionKey.channel();
                            ByteBuffer readBuffer=ByteBuffer.allocate(1024);

                            int count=client.read(readBuffer);
                            if (count > 0){
                                readBuffer.flip();

                                Charset charset=Charset.forName("utf-8");
                                String receivedMessage = String.valueOf(charset.decode(readBuffer).array());
                                // 客户端发的消息
                                System.out.println(client + ": " + receivedMessage);

                                // 发送的key
                                String sendKey=null;
                                // 循环找到当前发送消息的客户端key
                                for(Map.Entry<String,SocketChannel> entry :clientMap.entrySet()){
                                   if(client==entry.getValue()){
                                       sendKey=entry.getKey();
                                       break;
                                   }
                                }

                                // 给所有建立连接的客户端 发送消息
                                for(Map.Entry<String,SocketChannel> entry :clientMap.entrySet()){
                                        SocketChannel value=entry.getValue();
                                        ByteBuffer writeBuffer=ByteBuffer.allocate(1024);
                                        // 发送者的key+ 发送的消息 写入bufer
                                        writeBuffer.put((selectionKey +":"+receivedMessage).getBytes());
                                        writeBuffer.flip();
                                        value.write(writeBuffer);
                                }
                            }
                        }else if(selectionKey.isWritable()){
                            // 通道可写
                        }

                    }catch (Exception e){
                        e.printStackTrace();
                    }
                });

                selectionKeys.clear();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
