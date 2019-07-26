package com.github.nio;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.concurrent.*;

public class NioClient {

    public static void main(String[] args) throws IOException {
        try {


            SocketChannel socketChannel = SocketChannel.open();
            socketChannel.configureBlocking(false);

            Selector selector = Selector.open();
            // 将 Channel 注册到 Selector 中, 并设置需要监听的事件
            // 客户端调用connect()并注册OP_CONNECT事件后，连接操作就会就绪
            socketChannel.register(selector, SelectionKey.OP_CONNECT);
            socketChannel.connect(new InetSocketAddress("127.0.0.1", 8899));

            while (true) {
                // 在刚初始化的Selector对象中，这三个集合都是空的。 通过Selector的select（）方法可以选择已经准备就绪的通道
                // 比如你对读就绪的通道感兴趣，那么select（）方法就会返回读事件已经就绪的那些通道
                /**
                 *  int select()：阻塞到至少有一个通道在你注册的事件上就绪了。
                 *  int select(long timeout)：和select()一样，但最长阻塞时间为timeout毫秒。
                 *  int selectNow()：非阻塞，只要有通道就绪就立刻返回。
                 */
                selector.select();
                Set<SelectionKey> keySet = selector.selectedKeys();

                for (SelectionKey selectionKey : keySet) {
                    //是否可连接，是返回 true
                    if (selectionKey.isConnectable()) {
                        SocketChannel client = (SocketChannel) selectionKey.channel();

                        // 判断此通道上的连接操作是否正在进行
                        if (client.isConnectionPending()) {
                            // 完成连接套接字通道的过程
                            client.finishConnect();

                             // 构建缓冲区
                            ByteBuffer writeBuffer = ByteBuffer.allocate(1024);

                            writeBuffer.put((LocalDateTime.now() + " 连接成功").getBytes());

                            writeBuffer.flip();
                            client.write(writeBuffer);
                            // 使用线程池
                            ThreadPoolExecutor executor=new ThreadPoolExecutor(1,1, 0L,TimeUnit.MILLISECONDS,new LinkedBlockingQueue<Runnable>());
                            executor.submit(() -> {
                                while (true) {
                                    try {
                                        writeBuffer.clear();
                                        InputStreamReader input = new InputStreamReader(System.in);
                                        BufferedReader br = new BufferedReader(input);

                                        String sendMessage = br.readLine();

                                        writeBuffer.put(sendMessage.getBytes());
                                        writeBuffer.flip();
                                        client.write(writeBuffer);
                                    } catch (Exception ex) {
                                        ex.printStackTrace();
                                    }
                                }
                            });
                        }

                        client.register(selector, SelectionKey.OP_READ);
                    } else if (selectionKey.isReadable()) {
                        // 通道可读
                        SocketChannel client = (SocketChannel) selectionKey.channel();

                        ByteBuffer readBuffer = ByteBuffer.allocate(1024);

                        int count = client.read(readBuffer);

                        if (count > 0) {
                            String receivedMessage = new String(readBuffer.array(), 0, count);
                            System.out.println(receivedMessage);
                        }
                    }
                }

                keySet.clear();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
