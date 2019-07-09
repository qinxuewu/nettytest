package com.github;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import java.net.InetSocketAddress;

/**
 * 服务端 启动
 * @author qxw
 * @version 1.00
 * @time  9/7/2019 下午 4:15
 */
public class EchoServer {
    private  final  int port;
    public  EchoServer(int port){
        this.port=port;
    }

    public static void main(String[] args)throws Exception {

        //      调用服务器的 start()方法
        new EchoServer(8088).start();
    }

    public void start() throws Exception {
        final EchoServerHandler serverHandler = new EchoServerHandler();
        // 创建Event-LoopGroup
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            // 创建Server-Bootstrap
            ServerBootstrap b = new ServerBootstrap();
            b.group(group)
                    // 指定所使用的 NIO传输 Channel
                    .channel(NioServerSocketChannel.class)
                    //  使用指定的端口设置字接字地址
                    .localAddress(new InetSocketAddress(port))
                    // 添加一个EchoServer-Handler 到子Channel的 ChannelPipeline
                    // 当一个新的连接被接受时，一个新的子 Channel 将会被创建，
                    .childHandler(new ChannelInitializer<SocketChannel>(){
                        @Override
                        public void initChannel(SocketChannel ch)throws Exception {
                            ch.pipeline().addLast(serverHandler);
                        }
                    });

            // 异步绑定服务器 调用 sync()方法阻塞 等待直到绑定完成
            ChannelFuture f = b.bind().sync();
            //  获取 Channel 的CloseFuture，并且阻塞当前线程直到它完成
            f.channel().closeFuture().sync();
        }finally {
            // 关闭 EventLoopGroup 释放所有的资源
            group.shutdownGracefully().sync();
        }
    }
}
