package com.github.test4;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * Netty读写监测机制与长连接要素
 *
 * @author qinxuewu
 * @create 19/7/15上午12:35
 * @since 1.0.0
 */


public class MyCheckServer {

    public static void main(String[] args) throws Exception {
        // 建立连接的线程
        EventLoopGroup bossGroup=new NioEventLoopGroup();
        // 处理连接
        EventLoopGroup workGroup=new NioEventLoopGroup();
        try {
            ServerBootstrap serverBootstrap=new ServerBootstrap();
            serverBootstrap.group(bossGroup,workGroup).channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO)) // 日志haner
                    .childHandler(new MyCheckServerInitailzr());

            ChannelFuture future=serverBootstrap.bind(8899).sync();
            future.channel().closeFuture().sync();
        }finally {
            // 优雅关闭
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }
}
